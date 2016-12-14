package com.farouk.bengharssallah.rss.parser.cnn;

import java.io.InputStream;
import java.text.ParseException;
import java.util.LinkedList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.model.Picture;
import com.farouk.bengharssallah.rss.parser.RSSParser;
import com.farouk.bengharssallah.rss.util.Constants;

		/** 
		 * <p>{@link CNNIParser} is a {@link RSSParser}  to extract data extracted from CNBC.com
		 * RSS feed</p>
		 * 
		 ***/

public class CNNParser extends RSSParser {
    
    private static final String edt_timezone = " EDT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String cnn_intel = "CNN.com - RSS Channel - Intl Homepage - ";
	private static final String cnn_rss = "CNN.com - RSS Channel - ";
	
	public CNNParser(String feedUrl) {
		  super(feedUrl);
	         }

	 public LinkedList<CNNNews> getNews() {
	    	   LinkedList<CNNNews> list = new LinkedList<CNNNews>();
		       try {
		    	      boolean isFeedHeader = true;
				      String title = null;
				      String description = null;
				      String category = null;
				      String date = null;
				      String link = null;
				      Picture content_picture_144 = null;
				      Picture content_picture_169 = null;
				      Picture content_picture_186 = null;
				      Picture content_picture_250 = null;
				      Picture content_picture_300 = null;
				      Picture content_picture_324 = null;
				      Picture content_picture_360 = null;
				      Picture content_picture_480 = null;
				      Picture content_picture_552 = null;
				      Picture content_picture_619 = null;
					      
					      // First create a new XMLInputFactory
					      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
					      // Setup a new eventReader
					      InputStream in = read();
					      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
					      // read the XML document
					      while (eventReader.hasNext()) {
									        XMLEvent event = eventReader.nextEvent();
									        if (event.isStartElement()) {
													          String localPart = event.asStartElement().getName() .getLocalPart();
													          if(localPart.equals(Constants.CHANNEL) && isFeedHeader){
														        	  isFeedHeader = false;
													        	      event = eventReader.nextEvent();
													        	      category = getCharacterData(event, eventReader);
													        	      for(int i=0; i<41; i++){
													        	    	         event = eventReader.nextEvent();
													        	                      }
												                               }
													          if(localPart.equals(Constants.ITEM)){
													               //   event = eventReader.nextEvent();
													                           }
													          if(localPart.equals(Constants.TITLE)){
													        	     title = getCharacterData(event, eventReader);
													                              }
													          if(localPart.equals(Constants.DESCRIPTION)){
													                  description = getCharacterData(event, eventReader);
													                              }
													          if(localPart.equals(Constants.GUID)){
											                           link = getCharacterData(event, eventReader);
											                              }
													          if(localPart.equals(Constants.PUB_DATE)){
													                   date = getCharacterData(event, eventReader);
													                      }
													          if(localPart.equals(Constants.MEDIA_CONTENT)){
													        	       if(content_picture_619 == null) 
													        	    	                      content_picture_619 = getPicture(event, eventReader);
													        	       else if(content_picture_300 == null) 
													        	    	                      content_picture_300 = getPicture(event, eventReader);
													        	       else if(content_picture_552 == null) 
													        	    	                      content_picture_552 = getPicture(event, eventReader);
													        	       else if(content_picture_480 == null) 
													        	    	                     content_picture_480 = getPicture(event, eventReader);
													        	       else if(content_picture_324 == null) 
													        	    	                      content_picture_324 = getPicture(event, eventReader);
													        	       else if(content_picture_250 == null) 
													        	    	                      content_picture_250 = getPicture(event, eventReader);
													        	       else if(content_picture_360 == null) 
													        	    	                       content_picture_360 = getPicture(event, eventReader);
													        	       else if(content_picture_169 == null){
													        	    	                       content_picture_169 = getPicture(event, eventReader);
													        	                               content_picture_250 = null;
													        	                                    }
													        	       else if(content_picture_186 == null) 
													        	    	                       content_picture_186 = getPicture(event, eventReader);
													        	       else if(content_picture_144 == null) 
													        	    	                       content_picture_144 = getPicture(event, eventReader);
												                                }
									                   } 
									        else if (event.isEndElement()) {
													          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
														        	    CNNNews news = new CNNNews();
													        	        news.setTitle(title);
													        	           {
													        	        if(category.contains(cnn_intel)) 
											        	        	                       news.setCategory(category.replace(cnn_intel, ""));
													        	        else if(category.contains(cnn_rss))
													        	        	                news.setCategory(category.replace(cnn_rss, ""));
													        	       
													        	           }
													        	        if(!description.equals("<"))  
													        	                           news.setDescription(description);
													        	        news.setDate(formatDate(date.replace(edt_timezone, ""), date_format));
													        	        news.setLink(link);
													        	        news.setContent_picture_144(content_picture_144);
													        	        news.setContent_picture_169(content_picture_169);
													        	        news.setContent_picture_186(content_picture_186);
													        	        news.setContent_picture_250(content_picture_250);
													        	        news.setContent_picture_300(content_picture_300);
													        	        news.setContent_picture_324(content_picture_324);
													        	        news.setContent_picture_360(content_picture_360);
													        	        news.setContent_picture_480(content_picture_480);
													        	        news.setContent_picture_552(content_picture_552);
													        	        news.setContent_picture_619(content_picture_619);
													        	           {  
													        	        if(news.getContent_picture_619() != null)
									        	                                              news.setImage(news.getContent_picture_619().getSrc());
									        	                        else if(news.getContent_picture_552() != null)
					        	                                                              news.setImage(news.getContent_picture_552().getSrc());
									        	                        else if(news.getContent_picture_480() != null)
					        	                                                              news.setImage(news.getContent_picture_480().getSrc());
									        	                        else if(news.getContent_picture_360() != null)
					        	                                                              news.setImage(news.getContent_picture_360().getSrc());
									        	                        else if(news.getContent_picture_324() != null)
					        	                                                              news.setImage(news.getContent_picture_324().getSrc());
									        	                        else if(news.getContent_picture_300() != null)
	        	                                                                              news.setImage(news.getContent_picture_300().getSrc());
									        	                        else if(news.getContent_picture_250() != null)
	        	                                                                              news.setImage(news.getContent_picture_250().getSrc());
									        	                        else if(news.getContent_picture_186() != null)
	        	                                                                              news.setImage(news.getContent_picture_186().getSrc());
									        	                        else if(news.getContent_picture_169() != null)
                                                                                              news.setImage(news.getContent_picture_169().getSrc());
									        	                        else if(news.getContent_picture_144() != null)
                                                                                              news.setImage(news.getContent_picture_144().getSrc());
														        	         }
													        	        news.setReference(generateReference(news, Constants.CNN));
															            list.add(news);
															              {
													                    content_picture_144 = null;
																        content_picture_169 = null;
																        content_picture_186 = null;
																        content_picture_250 = null;
																        content_picture_300 = null;
																        content_picture_324 = null;
																        content_picture_360 = null;
																        content_picture_480 = null;
																        content_picture_552 = null;
																        content_picture_619 = null;
															              }
															            event = eventReader.nextEvent();
													                    continue;
									                                       }
									                   }
					                         }
		                            } 
		       catch (XMLStreamException e) {
		                               throw new RuntimeException(e);
		                            } 
		       catch (ParseException e) {
				                       e.printStackTrace();
			}
		      return list;
		  }
    }
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

public class CNNIParser extends RSSParser {

	    private static final String edt_timezone = " EDT";
		private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
		private static final String cnn_com = "CNN.com - ";
	
	public CNNIParser(String feedUrl) {
		  super(feedUrl);
	         }
	
	       @Override  
	 public LinkedList<CNNINews> getNews() {
	    	   LinkedList<CNNINews> list = new LinkedList<CNNINews>();
		       try {
		    	      boolean isFeedHeader = true;
				      String title = null;
				      String description = null;
				      String category = null;
				      String date = null;
				      String link = null;
				      Picture thumbnail_picture = null;
				      Picture content_picture = null;
					      
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
													          if(localPart.equals(Constants.MEDIA_THUMBNAIL)){
													        	       if(thumbnail_picture == null)
											        	                                  thumbnail_picture = getPicture(event, eventReader);
											                               }
											              if(localPart.equals(Constants.MEDIA_CONTENT)){
											        	                                  content_picture = getPicture(event, eventReader);
											                               }
									                   } 
									        else if (event.isEndElement()) {
													          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
													        	        CNNINews news = new CNNINews();
													        	        news.setTitle(title);
													        	           {
													        	        if(category.contains(cnn_com))
													        	        	               news.setCategory(category.replace(cnn_com, ""));
													        	           }
													        	        news.setDescription(description);
													        	        news.setDate(formatDate(date.replace(edt_timezone, ""), date_format));
													        	        news.setLink(link);
													        	        news.setThumbnail_picture(thumbnail_picture);
													        	        news.setContent_picture(content_picture);
													        	           {
													        	        if(news.getContent_picture() != null)
									        	                                              news.setImage(news.getContent_picture().getSrc());
									        	                        else if(news.getThumbnail_picture() != null)
					        	                                                              news.setImage(news.getThumbnail_picture().getSrc());
														        	        }
													        	        news.setReference(generateReference(news, Constants.CNNI));
															            list.add(news);
															               {
														                thumbnail_picture = null;
																	    content_picture = null;
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
package com.farouk.bengharssallah.rss.parser.abc;

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
		 * <p>{@link ABCParser} is a {@link RSSParser}  to extract data extracted from ABC.com
		 * RSS feed</p>
		 * 
		 ***/

public class ABCParser extends RSSParser {
	
	private static final String rss_feed = " RSS Feed";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";

	       public ABCParser(String feedUrl) {
		               super(feedUrl);
	            }

		@Override  
	 public LinkedList<ABCNews> getNews() {
						   LinkedList<ABCNews> list = new LinkedList<ABCNews>();
					       try {
					    	          boolean isHeader = true;
					    	          String title = null;
								      String category = null;
								      String description = null;
								      String date = null;
								      String link = null;
								      Picture thumbnail_picture = null;
								      Picture content_picture_1210 = null;
								      Picture content_picture_705 = null;
								      Picture content_picture_627 = null;
								      Picture content_picture_1253 = null;
								      Picture content_picture_1400 = null;
								  	
								  	  
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
																          if(localPart.equals(Constants.CHANNEL) && isHeader){
																        	      event = eventReader.nextEvent();
																        	      event = eventReader.nextEvent();
																        	      category = getCharacterData(event, eventReader);
																        	      int i=0;
																        	      while(i<14){
																        	    	           event = eventReader.nextEvent();
																        	    	           i++;
																        	                  }
																        	      isHeader = false;
																          }
																          if(localPart.equals(Constants.ITEM)){
																              event = eventReader.nextEvent();
																            }
																          if(localPart.equals(Constants.TITLE)){
																                  title = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(Constants.LINK)){
															                       link = getCharacterData(event, eventReader);
															                              }
																          if(localPart.equals(Constants.DESCRIPTION)){
																                  description = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(Constants.PUB_DATE)){
																                   date = getCharacterData(event, eventReader);
																                               }
																          if(localPart.equals(Constants.MEDIA_CONTENT)){
																        	       if(content_picture_1210 == null) 
																        	    	                    content_picture_1210 = getPicture(event, eventReader);
																        	       else if(content_picture_705 == null) 
																        	    	                    content_picture_705 = getPicture(event, eventReader);
																        	       else if(content_picture_627 == null) 
																        	    	                    content_picture_627 = getPicture(event, eventReader);
																        	       else if(content_picture_1253 == null) 
																        	    	                    content_picture_1253 = getPicture(event, eventReader);
																        	       else if(content_picture_1400 == null) 
																        	    	                    content_picture_1400 = getPicture(event, eventReader);
																                               }
																          if(localPart.equals(Constants.MEDIA_THUMBNAIL)){
															        	           thumbnail_picture = getPicture(event, eventReader);
															                               }
												                   } 
												        else if (event.isEndElement()) {
																          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
																        	        ABCNews news = new ABCNews();
																        	        news.setTitle(title);
																        	        news.setCategory(category.replace(rss_feed, ""));
																        	        news.setDescription(description);
																        	        news.setDate(formatDate(date.substring(0, date.lastIndexOf(":") + 2), date_format));
																        	        news.setLink(link);
																        	        news.setThumbnail_picture(thumbnail_picture);
																        	        news.setContent_picture_1210(content_picture_1210);
																        	        news.setContent_picture_705(content_picture_705);
																        	        news.setContent_picture_627(content_picture_627);
																        	        news.setContent_picture_1253(content_picture_1253);
																        	        news.setContent_picture_1400(content_picture_1400);
																        	                {
																        	        if(news.getContent_picture_627() != null)
																        	        	                 news.setImage(news.getContent_picture_627().getSrc());
												        	                        else if(news.getContent_picture_705() != null)
								        	                                                              news.setImage(news.getContent_picture_705().getSrc());
												        	                        else if(news.getContent_picture_1210() != null)
								        	                                                              news.setImage(news.getContent_picture_1210().getSrc());
												        	                        else if(news.getContent_picture_1253() != null)
								        	                                                              news.setImage(news.getContent_picture_1253().getSrc());
												        	                        else if(news.getContent_picture_1400() != null)
								        	                                                              news.setImage(news.getContent_picture_1400().getSrc());
																        	                 }
																        	        news.setReference(generateReference(news, Constants.ABC));
																		            list.add(news);
																		                 {
															                        thumbnail_picture = null;
																			        content_picture_1210 = null;
																			        content_picture_705 = null;
																			        content_picture_627 = null;
																			        content_picture_1253 = null;
																			        content_picture_1400 = null;     	 
																		                 }
																		            event = eventReader.nextEvent();
																                    continue;
												                                       }
												                   }
								                         }
					                            } 
					       catch (XMLStreamException e) {
					                               throw new RuntimeException(e);
					                            } catch (ParseException e) {
							e.printStackTrace();
						}
					      return list;
		          }
	       
     }
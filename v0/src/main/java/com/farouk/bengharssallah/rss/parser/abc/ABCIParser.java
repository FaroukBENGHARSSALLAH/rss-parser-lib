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
		 * <p>{@link ABCIParser} is a custom type of  {@link ABCParser}  to extract data extracted from ABC.com
		 * RSS feed</p>
		 * 
		 ***/

public class ABCIParser extends RSSParser {
	
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String abc_news = "ABC News: "; 

	       public ABCIParser(String feedUrl) {
		               super(feedUrl);
	            }

		@Override  
	 public LinkedList<ABCINews> getNews() {
						   LinkedList<ABCINews> list = new LinkedList<ABCINews>();
					       try {
					    	          boolean isFeedHeader = true;
								      String title = null;
								      String category = null;
								      String description = null;
								      String date = null;
								      String link = null;
								      Picture thumbnail_picture_992 = null;
								      Picture thumbnail_picture_608 = null ;
								      Picture thumbnail_picture_384 = null;
								      Picture thumbnail_picture_240 = null;
								      Picture thumbnail_picture_144 = null;
								      
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
															                                 }
																          if(localPart.equals(Constants.ITEM)){
																                  event = eventReader.nextEvent();
																                              }
																          if(localPart.equals(Constants.TITLE)){
																                  title = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(Constants.DESCRIPTION)){
																                  description = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(Constants.LINK)){
																                  link = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(Constants.PUB_DATE)){
																                   date = getCharacterData(event, eventReader);
																                               }
																          if(localPart.equals(Constants.MEDIA_THUMBNAIL)){
																        	       if(thumbnail_picture_384 == null) 
																        	    	                 thumbnail_picture_384 = getPicture(event, eventReader);
																        	       else if(thumbnail_picture_144 == null){
																        	    	                 thumbnail_picture_144 = getPicture(event, eventReader);
																        	    	                 thumbnail_picture_384 = null;
																        	                                 }
																        	       else if(thumbnail_picture_240 == null) 
																        	    	                 thumbnail_picture_240 = getPicture(event, eventReader);
																        	       else if(thumbnail_picture_608 == null) 
																        	    	                 thumbnail_picture_608 = getPicture(event, eventReader);
																        	       else if(thumbnail_picture_992 == null) 
																        	    	                 thumbnail_picture_992 = getPicture(event, eventReader);
																                               }
												                   } 
												        else if (event.isEndElement()) {
																          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
																        	        ABCINews news = new ABCINews();
																        	        news.setTitle(title);
																        	        news.setCategory(category.replace(abc_news, ""));
																        	        news.setDescription(description);
																        	        news.setDate(formatDate(date.substring(0, date.lastIndexOf(":") + 2), date_format));
																        	        news.setLink(link);
																        	        news.setThumbnail_picture_144(thumbnail_picture_144);
																        	        news.setThumbnail_picture_240(thumbnail_picture_240);
																        	        news.setThumbnail_picture_384(thumbnail_picture_384);
																        	        news.setThumbnail_picture_608(thumbnail_picture_608);
																        	        news.setThumbnail_picture_992(thumbnail_picture_992);
																        	                   {
																        	        if(news.getThumbnail_picture_144() != null)
												        	                                              news.setImage(news.getThumbnail_picture_144().getSrc());
												        	                        else if(news.getThumbnail_picture_240() != null)
								        	                                                              news.setImage(news.getThumbnail_picture_240().getSrc());
												        	                        else if(news.getThumbnail_picture_384() != null)
								        	                                                              news.setImage(news.getThumbnail_picture_384().getSrc());
												        	                        else if(news.getThumbnail_picture_608() != null)
								        	                                                              news.setImage(news.getThumbnail_picture_608().getSrc());
												        	                        else if(news.getThumbnail_picture_992() != null)
								        	                                                              news.setImage(news.getThumbnail_picture_992().getSrc());
																	        	                 }
																        	        news.setReference(generateReference(news, Constants.ABCI));
																		            list.add(news);
																		              {
																		            thumbnail_picture_144 = null ;
																		            thumbnail_picture_240 = null;
																		            thumbnail_picture_384 = null;
																		            thumbnail_picture_608 = null;
																		            thumbnail_picture_992 = null;
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
					    	                       throw new RuntimeException(e);
						                        }
					       return list;
		          }
	       
     }
package com.farouk.bengharssallah.test.application.rss.abc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.util.Constants;


public class ABCIParser  {
	
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String abc_news = "ABC News: "; 
	private static final String src_ref = "a23i";
	
	protected final  URL url;

	       public ABCIParser(String feedUrl) {
	    	   try {
		                   this.url = new URL(feedUrl);
		               } 
		       catch (MalformedURLException e) {
		                   throw new RuntimeException(e);
		               }
	            }

	 public LinkedList<ABCINews> getNews() {
	    	   LinkedList<ABCINews> list = new LinkedList<ABCINews>();
		       try {
		    	          boolean isFeedHeader = true;
					      String title = null;
					      String category = null;
					      String description = null;
					      String date = null;
					      String link = null;
					      String thumbnail_picture_992 = null;
					      String thumbnail_picture_608 = null ;
					      String thumbnail_picture_384 = null;
					      String thumbnail_picture_240 = null;
					      String thumbnail_picture_144 = null;
					      
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
													        	    	                 thumbnail_picture_384 = getThumbnailPictureData(event, eventReader);
													        	       else if(thumbnail_picture_144 == null){
													        	    	                 thumbnail_picture_144 = getThumbnailPictureData(event, eventReader);
													        	    	                 thumbnail_picture_384 = null;
													        	                                 }
													        	       else if(thumbnail_picture_240 == null) 
													        	    	                 thumbnail_picture_240 = getThumbnailPictureData(event, eventReader);
													        	       else if(thumbnail_picture_608 == null) 
													        	    	                 thumbnail_picture_608 = getThumbnailPictureData(event, eventReader);
													        	       else if(thumbnail_picture_992 == null) 
													        	    	                 thumbnail_picture_992 = getThumbnailPictureData(event, eventReader);
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
									        	                                              news.setImage(news.getThumbnail_picture_144());
									        	                        else if(news.getThumbnail_picture_240() != null)
					        	                                                              news.setImage(news.getThumbnail_picture_240());
									        	                        else if(news.getThumbnail_picture_384() != null)
					        	                                                              news.setImage(news.getThumbnail_picture_384());
									        	                        else if(news.getThumbnail_picture_608() != null)
					        	                                                              news.setImage(news.getThumbnail_picture_608());
									        	                        else if(news.getThumbnail_picture_992() != null)
					        	                                                              news.setImage(news.getThumbnail_picture_992());
														        	                 }
													        	        news.setReference(generateReference(news, src_ref));
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
	 
	 
	 
	 
	 
					 protected String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
						    String result = "";
						    event = eventReader.nextEvent();
						    if (event instanceof Characters) {
						                          result = event.asCharacters().getData();
						                  }
						    return result;
				    }
				
				protected String getThumbnailPictureData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
						    String result = "";
						    @SuppressWarnings("unchecked")
							Iterator<Attribute> attributes =  event.asStartElement().getAttributes();
						    while(attributes.hasNext()) {
										    	Attribute attribute = attributes.next();
										    	result += " " + attribute.getName() + "='" + attribute.getValue() + "'";
					                        }
						    return result;
				    }
		
				
				protected InputStream read() {
						    try {
						                 return url.openStream();
						                 } 
						    catch (IOException e) {
						                 throw new RuntimeException(e);
						                 }
				   }
				
				
				
				 private String formatDate(String date_value, String format) throws ParseException{
			           Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(date_value);
			           return new SimpleDateFormat("HH:mm dd/MM").format(date);
                    }
				 
				 
				 private String generateReference(ABCINews news, String source) {
			           String cat = news.getCategory().substring(0,3).toLowerCase();
			           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
			           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
                    }
	       
     }
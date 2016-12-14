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
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.util.Constants;


public class ABCParser {
	
	private static final String rss_feed = " RSS Feed";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String src_ref = "a23";

    protected final  URL url;
	

    public ABCParser(String feedUrl) {
			    try {
			                   this.url = new URL(feedUrl);
			               } 
			    catch (MalformedURLException e) {
			                   throw new RuntimeException(e);
			               }
	           }

    protected String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
					    String result = "";
					    event = eventReader.nextEvent();
					    if (event instanceof Characters && !event.asCharacters().getData().equals("'")) {
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
	  
    protected String getContentPictureData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
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
	
	 public LinkedList<ABCNews> getNews() {
	    	   LinkedList<ABCNews> list = new LinkedList<ABCNews>();
		       try {
		    	          boolean isHeader = true;
					      String title = null;
					      String category = null;
					      String description = null;
					      String date = null;
					      String link = null;
					      String thumbnail_picture = null;
					      String content_picture_1210 = null ;
					  	  String content_picture_705 = null;
					  	  String content_picture_627 = null;
					  	  String content_picture_1253 = null;
					  	  String content_picture_1400 = null;
					  	  
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
													        	    	                    content_picture_1210 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_705 == null) 
													        	    	                    content_picture_705 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_627 == null) 
													        	    	                    content_picture_627 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_1253 == null) 
													        	    	                    content_picture_1253 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_1400 == null) 
													        	    	                    content_picture_1400 = getContentPictureData(event, eventReader);
													                               }
													          if(localPart.equals(Constants.MEDIA_THUMBNAIL)){
												        	           thumbnail_picture = getThumbnailPictureData(event, eventReader);
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
									        	                                              news.setImage(news.getContent_picture_627());
									        	                        else if(news.getContent_picture_705() != null)
					        	                                                              news.setImage(news.getContent_picture_705());
									        	                        else if(news.getContent_picture_1210() != null)
					        	                                                              news.setImage(news.getContent_picture_1210());
									        	                        else if(news.getContent_picture_1253() != null)
					        	                                                              news.setImage(news.getContent_picture_1253());
									        	                        else if(news.getContent_picture_1400() != null)
					        	                                                              news.setImage(news.getContent_picture_1400());
													        	                 }
													        	        news.setReference(generateReference(news, src_ref));
															            list.add(news);
															               {
													                    thumbnail_picture = null;
																        content_picture_1210 = null ;
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
	 
	 
	   private String formatDate(String date_value, String format) throws ParseException{
				           Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(date_value);
				           return new SimpleDateFormat("HH:mm dd/MM").format(date);
            }
	   
	   
	   private String generateReference(ABCNews news, String source) {
           String cat = news.getCategory().substring(0,3).toLowerCase();
           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
             }

}
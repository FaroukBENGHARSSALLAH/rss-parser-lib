package com.farouk.bengharssallah.test.application.rss.cnn;

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


public class CNNParser {

    protected final  URL url;
    
    private static final String edt_timezone = " EDT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String cnn_intel = "CNN.com - RSS Channel - Intl Homepage - ";
	private static final String cnn_rss = "CNN.com - RSS Channel - ";
	private static final String src_ref = "3nn";
	
    public CNNParser(String feedUrl) {
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
					    if (event instanceof Characters) {
					                          result = event.asCharacters().getData();
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
	
	 public LinkedList<CNNNews> getNews() {
	    	   LinkedList<CNNNews> list = new LinkedList<CNNNews>();
		       try {
		    	      boolean isFeedHeader = true;
				      String title = null;
				      String description = null;
				      String category = null;
				      String date = null;
				      String link = null;
				      String content_picture_144 = null;
				  	  String content_picture_169 = null;
				  	  String content_picture_186 = null;
				  	  String content_picture_250 = null;
				  	  String content_picture_300 = null;
				  	  String content_picture_324 = null;
				  	  String content_picture_360 = null;
				  	  String content_picture_480 = null;
				  	  String content_picture_552 = null;
				  	  String content_picture_619 = null;
					      
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
													        	    	                      content_picture_619 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_300 == null) 
													        	    	                      content_picture_300 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_552 == null) 
													        	    	                      content_picture_552 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_480 == null) 
													        	    	                     content_picture_480 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_324 == null) 
													        	    	                      content_picture_324 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_250 == null) 
													        	    	                      content_picture_250 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_360 == null) 
													        	    	                       content_picture_360 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_169 == null){
													        	    	                       content_picture_169 = getContentPictureData(event, eventReader);
													        	                               content_picture_250 = null;
													        	                                    }
													        	       else if(content_picture_186 == null) 
													        	    	                       content_picture_186 = getContentPictureData(event, eventReader);
													        	       else if(content_picture_144 == null) 
													        	    	                       content_picture_144 = getContentPictureData(event, eventReader);
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
									        	                                              news.setImage(news.getContent_picture_619());
									        	                        else if(news.getContent_picture_552() != null)
					        	                                                              news.setImage(news.getContent_picture_552());
									        	                        else if(news.getContent_picture_480() != null)
					        	                                                              news.setImage(news.getContent_picture_480());
									        	                        else if(news.getContent_picture_360() != null)
					        	                                                              news.setImage(news.getContent_picture_360());
									        	                        else if(news.getContent_picture_324() != null)
					        	                                                              news.setImage(news.getContent_picture_324());
									        	                        else if(news.getContent_picture_300() != null)
	        	                                                                              news.setImage(news.getContent_picture_300());
									        	                        else if(news.getContent_picture_250() != null)
	        	                                                                              news.setImage(news.getContent_picture_250());
									        	                        else if(news.getContent_picture_186() != null)
	        	                                                                              news.setImage(news.getContent_picture_186());
									        	                        else if(news.getContent_picture_169() != null)
                                                                                              news.setImage(news.getContent_picture_169());
									        	                        else if(news.getContent_picture_144() != null)
                                                                                              news.setImage(news.getContent_picture_144());
														        	         }
													        	        news.setReference(generateReference(news, src_ref));
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
	 
	 
	   private String formatDate(String date_value, String format) throws ParseException{
				           Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(date_value);
				           return new SimpleDateFormat("dd/MM HH:mm").format(date);
           }
	   
	   
	   private String generateReference(CNNNews news, String source) {
				           String cat = news.getCategory().substring(0,3).toLowerCase();
				           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
				           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
             }

}
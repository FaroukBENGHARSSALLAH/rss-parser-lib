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


public class CNNIParser {

    protected final  URL url;
    
    private static final String edt_timezone = " EDT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String cnn_com = "CNN.com - ";
	private static final String src_ref = "3nni";
	
    public CNNIParser(String feedUrl) {
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
	
	 public LinkedList<CNNINews> getNews() {
	    	   LinkedList<CNNINews> list = new LinkedList<CNNINews>();
		       try {
		    	      boolean isFeedHeader = true;
				      String title = null;
				      String description = null;
				      String category = null;
				      String date = null;
				      String link = null;
				      String thumbnail_picture = null;
				      String content_picture = null;
					      
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
											        	                                  thumbnail_picture = getThumbnailPictureData(event, eventReader);
											                               }
											              if(localPart.equals(Constants.MEDIA_CONTENT)){
											        	               content_picture = getContentPictureData(event, eventReader);
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
									        	                                              news.setImage(news.getContent_picture());
									        	                        else if(news.getThumbnail_picture() != null)
					        	                                                              news.setImage(news.getThumbnail_picture());
														        	        }
													        	        news.setReference(generateReference(news, src_ref));
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
	   
	   
	   private String generateReference(CNNINews news, String source) {
				           String cat = news.getCategory().substring(0,3).toLowerCase();
				           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
				           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
             }

}
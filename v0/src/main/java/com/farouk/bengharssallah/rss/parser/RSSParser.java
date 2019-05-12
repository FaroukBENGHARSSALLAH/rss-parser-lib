package com.farouk.bengharssallah.rss.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.model.News;
import com.farouk.bengharssallah.rss.model.Picture;

				/** 
				 * {@link RSSParser} is an abstract class which integrates some APIs related to 
				 * RSS data extraction. All concrete RSS feed parser must inherit from this abstract class .
				 * 
				 ***/

public abstract class RSSParser {
	
	          /** The url variable is a {@link URL} to represent the current RSS feed xml URL being parsed. 
	           * It should be unique during class execution as it parses an online XML stream content.**/
	     protected final  URL url;
	     
	          /** a static {@link String} variable**/
	     private static final String attr_url = "url";
	          /** a static {@link String} variable**/
	     private static final String attr_height = "height";
	         /** a static {@link String} variable**/
	     private static final String attr_width = "width";
	     
	     
	     /** 
			 * {@link RSSParser} constructor. It should initialize url value.
			 *	@param feedUrl   {@link String}	feed xml link
			 ***/
	     
	     public RSSParser(String feedUrl) {
			    try {
			                   this.url = new URL(feedUrl);
			               } 
			    catch (MalformedURLException e) {
			                   throw new RuntimeException(e);
			               }
	           }
	     
	     
	     
	     
	       /** 
			 * This abstract method will be redefined in every concrete RSS feed parser children classes
			 * 'according' to the feed specification. Return value type should inherit 
			 *  form the abstract class {@link News}.
			 *  @return {@link LinkedList} of {@link News}
			 ***/
	
	     public abstract LinkedList<? extends News> getNews();
	     
	     
	     
	      /** 
			 * This method will open the channel stream related to the RSS feed, it's called
			 * inside the implemented method getNews() in children classes.
			 *  form the abstract class {@link News}.
			 *  @return {@link InputStream}
			 ***/
	     
	     protected InputStream read() {
			    try {
			                 return url.openStream();
			                 } 
			    catch (IOException e) {
			                 throw new RuntimeException(e);
			                 }
               }
	     
	     
	      /** 
			 * This method will extract the RSS feed item data related to a particular attribute. It's called
			 * inside the implemented method getNews() in children classes when validating the following condition and using the {@link XMLEventReader} instance.   
			 *	@param event   {@link XMLEvent}	  is used to refer XML reader current location tag
			 *	@param eventReader   {@link XMLEventReader}    is used to move to the next tag
			 *  @return {@link String}
			 **/
	  
	     protected String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
						    String result = "";
						    event = eventReader.nextEvent();
						    if (event instanceof Characters) {
						                          result = event.asCharacters().getData();
						                  }
						    return result;
		               }
	     
	     
	     
	     /** 
			 * This method will extract the RSS feed {@link Picture} item data related to a particular image attribute. It's called
			 * inside the implemented method getNews() in children classes when validating the following condition and using the {@link XMLEventReader} instance.   
			 *	@param event    {@link XMLEvent}	  is used to refer XML reader current location tag
			 *	@param eventReader   {@link XMLEventReader}  is used to move to the next tag
			 *  @return {@link Picture}
			 **/
	     
	     
		  
	     protected Picture getPicture(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
						    Picture picture = new Picture();
						    @SuppressWarnings("unchecked")
							Iterator<Attribute> attributes =  event.asStartElement().getAttributes();
						    while(attributes.hasNext()) {
										    	Attribute attribute = attributes.next();
										    	 if(attribute.getName().getLocalPart().equals(attr_url))
										    		                 picture.setSrc(attribute.getValue());
										    	 else if(attribute.getName().getLocalPart().equals(attr_height))
						    		                                 picture.setHeight(attribute.getValue());
										    	 else if(attribute.getName().getLocalPart().equals(attr_width))
						    		                                picture.setWidth(attribute.getValue());
					                        }
						    return picture;
			           }
	     
	     
	     
	       /** 
			 * It returns a {@link String} date value according to a date format.
			 *	@param date_value   {@link String}  , initial date value
			 *	@param format   {@link String} , returned date format
			 *  @return {@link String}
			 **/
	     
	       
	     protected String formatDate(String date_value, String format) throws ParseException{
			    	      Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(date_value);
			    	      return new SimpleDateFormat("HH:mm dd/MM").format(date);
	    	         }
	     
	     
	     
	       /** 
			 * It returns a {@link String} reference identifying the current {@link News} news 
			 * instance.
			 *	@param news   {@link News}  , news instance
			 *	@param source   {@link String}  , news rss source 
			 *  @return {@link String}
			 **/
	     
	     protected String generateReference(News news, String source) {
					           String cat = news.getCategory().substring(0,3).toLowerCase();
					           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
					           return cat + dat + source;
	                     }
               }
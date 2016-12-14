package com.farouk.bengharssallah.test.application.rss.nbc;

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


public class NBCParser {

    protected final  URL url;
    
    private static final String four_timezone = " -0400";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String clean_desc_quote = "&quot;";
	private static final String clean_desc_br = "/><br />";
	private static final String clean_desc_br_i = "<br/><i>";
	private static final String clean_desc_photo = ".<br/><br/>Photo Credit:";
	private static final String categ_top = "top";
	private static final String categ_top_str = "top stories";
	private static final String categ_sport = "sport";
	private static final String categ_tech = "tech";
	private static final String src_ref = "n23";
	

    public NBCParser(String feedUrl) {
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
	
	 public LinkedList<NBCNews> getNews() {
	    	   LinkedList<NBCNews> list = new LinkedList<NBCNews>();
		       try {
		    	      boolean isFeedHeader = true;
		    	      String author = null;
				      String title = null;
				      String description = null;
				      String date = null;
				      String link = null;
				      String picture_summary = null;
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
														        	  for(int i=0; i<26; i++){
														        		              event = eventReader.nextEvent();
														        	              }
												                               }
													          if(localPart.equals(Constants.ITEM)){
													                  event = eventReader.nextEvent();
													            }
													          if(localPart.equals(Constants.CREATOR)){
												                       author = getCharacterData(event, eventReader);
												                              }
													          if(localPart.equals(Constants.TITLE)){
													        	      if(title == null)
													                                   title = getCharacterData(event, eventReader);
													        	      else
													        	    	               picture_summary = getCharacterData(event, eventReader);
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
													        	       if(thumbnail_picture == null)
											        	                                  thumbnail_picture = getThumbnailPictureData(event, eventReader);
											                               }
											                  if(localPart.equals(Constants.MEDIA_CONTENT)){
											        	                content_picture = getContentPictureData(event, eventReader);
											                               }
									                   } 
									        else if (event.isEndElement()) {
													          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
														        	    NBCNews news = new NBCNews();
													        	        news.setTitle(title);
													        	        news.setAuthor(author);
													        	        news.setDescription(cleanDescriptionValue(description));
													        	        news.setDate(formatDate(date.replace(four_timezone, ""), date_format));
													        	           {
													        	        if(url.getPath().contains(categ_top))	   
													        	                           news.setCategory(categ_top_str);
													        	        else if(url.getPath().contains(categ_sport))	   
										        	                                       news.setCategory(categ_sport);
													        	        else  if(url.getPath().contains(categ_tech))	   
										        	                                       news.setCategory(categ_tech);
													        	           }
													        	        news.setLink(link);
													        	        news.setPicture_summary(picture_summary);
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
															            title = null;
															            picture_summary = null;
															            thumbnail_picture = null;
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
				           return new SimpleDateFormat("HH:mm dd/MM").format(date);
          }
	   
	   private String cleanDescriptionValue(String value){
		                   value = value.replace(clean_desc_quote, "'");
		                   value =  value.substring(value.indexOf(clean_desc_br) + 8 );
		                   if(value.contains(clean_desc_br_i))
            	                            value = value.substring(0, value.indexOf(clean_desc_br_i));
		                   if(value.contains(clean_desc_photo))
		                	               value = value.substring(0, value.indexOf(clean_desc_photo));
			               return value;
	       }
	   
	   
	   private String generateReference(NBCNews news, String source) {
				           String cat = news.getCategory().substring(0,3).toLowerCase();
				           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
				           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
              }

}
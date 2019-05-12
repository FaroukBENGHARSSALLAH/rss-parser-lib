package com.farouk.bengharssallah.test.application.rss.cnbc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.util.Constants;


public class CNBCParser {

    
	private static final String desc_accent = "&#039;";
	private static final String gmt_timezone = " GMT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm";
	private static final String src_ref = "3n2c";
	
	protected final  URL url;
	

    public CNBCParser(String feedUrl) {
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
	
	 public LinkedList<CNBCNews> getNews() {
	    	   LinkedList<CNBCNews> list = new LinkedList<CNBCNews>();
		       try {
		    	          boolean isFeedHeader = true;
		    	          String title = null;
					      String description = null;
					      String date = null;
					      String link = null;
					      String category = null;
					      
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
														          for(int i=0; i<5; i++){
														        		         event = eventReader.nextEvent();
														        	          } 
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
									                   } 
									        else if (event.isEndElement()) {
													          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
													        	        CNBCNews news = new CNBCNews();
													        	        news.setTitle(title);
													        	        news.setCategory(category);
													        	        news.setDescription(description.replace(desc_accent, "'"));
													        	        news.setDate(formatDate(date.replace(gmt_timezone, ""), date_format));
													        	        news.setLink(link);
													        	        news.setReference(generateReference(news, src_ref));
															            list.add(news);
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
				           return new SimpleDateFormat("HH:mm dd/mm").format(date);
                  }
	   
	   private String generateReference(CNBCNews news, String source) {
				           String cat = news.getCategory().substring(0,3).toLowerCase();
				           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
				           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
                  }

}
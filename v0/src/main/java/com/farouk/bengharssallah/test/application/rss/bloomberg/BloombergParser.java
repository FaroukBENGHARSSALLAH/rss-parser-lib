package com.farouk.bengharssallah.test.application.rss.bloomberg;

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


public class BloombergParser {
	
    
	private static final String audio = " (Audio)";
	private static final String money = "money";
	private static final String bloomberg = "(Bloomberg) -- ";
	private static final String bloomberg_catherine_reports = " Bloomberg’s Catherine Cowdery reports on exchange-traded funds.";
	private static final String gmt_timezone = " GMT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String src_ref = "2lo";
	
	protected final  URL url;
	

    public BloombergParser(String feedUrl) {
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
	
	 public LinkedList<BloombergNews> getNews() {
	    	   LinkedList<BloombergNews> list = new LinkedList<BloombergNews>();
		       try {
					      boolean isFeedHeader = true;
		    	          String title = null;
					      String description = null;
					      String date = null;
					      String link = null;
					      String duration = null;
					      
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
												        	           for(int i=0; i<50; i++){
												        	    	             event = eventReader.nextEvent();
													        	                            }
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
													          if(localPart.equals(Constants.SUMMARY)){
													                  description = getCharacterData(event, eventReader);
													                              }
													          if(localPart.equals(Constants.PUB_DATE)){
													                   date = getCharacterData(event, eventReader);
													                               }
													          if(localPart.equals(Constants.DURATION)){
												                      duration = getCharacterData(event, eventReader);
												                               }
									                   } 
									        else if (event.isEndElement()) {
													          if (event.asEndElement().getName().getLocalPart() == (Constants.ITEM)) {
													        	        BloombergNews news = new BloombergNews();
													        	        news.setTitle(title.replace(audio, ""));
													        	        news.setCategory(money);
													        	        news.setDescription(description.replace(bloomberg, "").replaceAll(bloomberg_catherine_reports, ""));
													        	        news.setDate(formatDate(date.replace(gmt_timezone, "")));
													        	        news.setReference(generateReference(news, src_ref));
													        	        news.setLink(link);
													        	        news.setDuration(duration);
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
	 
	 
	   private String formatDate(String date_value) throws ParseException{
				           Date date = new SimpleDateFormat(date_format, Locale.ENGLISH).parse(date_value);
				           return new SimpleDateFormat("HH:mm dd/MM").format(date);
          }
	   
	   private String generateReference(BloombergNews news, String source) {
				           String cat = news.getCategory().substring(0,3).toLowerCase();
				           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
				           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
             }

}
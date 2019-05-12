package com.farouk.bengharssallah.test.application.rss.market;

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


public class MarketParser {

    protected final  URL url;
    
    private static final String null_timezone = " +0000";
    private static final String money = "money";
    private static final String desc_accent = "&quot";
    private static final String span = "<span";
    private static final String img_src = "img src";
    private static final String src_ref = "m1rk";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	
    public MarketParser(String feedUrl) {
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
	
	 public LinkedList<MarketNews> getNews() {
	    	   LinkedList<MarketNews> list = new LinkedList<MarketNews>();
		       try {
		    	      boolean isFeedHeader = true;
				      String title = null;
				      String description = null;
				      String date = null;
				      String link = null;
					      
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
													        	      for(int i=0; i<21; i++){
													        	    	          event = eventReader.nextEvent();
														        	                   }
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
														        	    MarketNews news = new MarketNews();
													        	        news.setTitle(title);
													        	        news.setCategory(money);
													        	        news.setDescription(cleanDescriptionValue(description));
													        	        news.setDate(formatDate(date.replace(null_timezone, ""), date_format));
													        	        news.setLink(link);
													        	        news.setChartOnePicture(getChartOnePictureData(description));
													        	        news.setChartTwoPicture(getChartTwoPictureData(description));
													        	               {
													        	        if(news.getChartOnePicture() != null)
									        	                                              news.setImage(news.getChartOnePicture());
									        	                        else if(news.getChartTwoPicture() != null)
					        	                                                              news.setImage(news.getChartTwoPicture());
															        	        }
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
		                            } 
		       catch (ParseException e) {
				                       e.printStackTrace();
			}
		      return list;
		  }
	 
	 
	 
		 private String cleanDescriptionValue(String value){
				         value = value.replace(desc_accent, "'");
				         if(value.contains(span))
				        	            value = value.substring(0, value.indexOf(span));
				         return value;
	                     }
		 
	 
	     protected String getChartOnePictureData(String source) throws XMLStreamException {
							    	 if(source.contains(img_src)){
							    		                    String image = source.substring((source.indexOf("src=\"") + 5), (source.indexOf(" alt=\"\"") - 1));
															return "src='" + image + "'" + "   height='425px'  width='680'";
							    	                                         }
						             return null;
                                }
	     
	     
	     protected String getChartTwoPictureData(String source) throws XMLStreamException {
	    	                        if(source.indexOf(img_src) != source.lastIndexOf(img_src)){
	    	                        	                     String image = source.substring((source.lastIndexOf("src=\"") + 5), (source.lastIndexOf(" alt=\"\"") - 1));
	    	                        	                     return image;
												    
	    	                                            }
							        return null;
                         }

	 
	 
	   private String formatDate(String date_value, String format) throws ParseException{
				           Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(date_value);
				           return new SimpleDateFormat("HH:mm dd/MM").format(date);
                        }
	   
	   
	   
	   private String generateReference(MarketNews news, String source) {
				           String cat = news.getCategory().substring(0,3).toLowerCase();
				           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
				           return cat + dat + source + Calendar.getInstance().get(Calendar.MILLISECOND);
                       }

    }
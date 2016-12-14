package com.farouk.bengharssallah.rss.parser.market;

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
		 * <p>{@link MarketParser} is a {@link RSSParser}  to extract data extracted from moneymovesmarkets.com
		 * RSS feed</p>
		 * 
		 ***/

public class MarketParser extends RSSParser {

	    private static final String null_timezone = " +0000";
	    private static final String money = "money";
	    private static final String desc_accent = "&quot";
	    private static final String span = "<span";
	    private static final String img_src = "img src";
		private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	
	public MarketParser(String feedUrl) {
		  super(feedUrl);
	         }
	
	       @Override  
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
												        	                                              news.setImage(news.getChartOnePicture().getSrc());
												        	                        else if(news.getChartTwoPicture() != null)
								        	                                                              news.setImage(news.getChartTwoPicture().getSrc());
																			        	       }
																        	        news.setReference(generateReference(news, Constants.MARKET));
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
			 

           protected Picture getChartOnePictureData(String source) throws XMLStreamException { 
        	                      if(source.contains(img_src)){
				    		                   Picture picture = new Picture();
				    		                   picture.setSrc(source.substring((source.indexOf("src=\"") + 5), (source.indexOf(" alt=\"\"") - 1)));
				    		                   picture.setWidth("680");
				    		                   picture.setHeight("425");
				    		                   return picture;
													}
							    return null;
                   }


           protected Picture getChartTwoPictureData(String source) throws XMLStreamException {
        	                    if(source.indexOf(img_src) != source.lastIndexOf(img_src)){
								    		  Picture picture = new Picture();
				    		                  picture.setSrc(source.substring((source.lastIndexOf("src=\"") + 5), (source.lastIndexOf(" alt=\"\"") - 1)));
				    		                  picture.setWidth("680");
				    		                  picture.setHeight("425");
				    		                  return picture;
													  }
															    		        
							    return null;
                  }
	    
    }
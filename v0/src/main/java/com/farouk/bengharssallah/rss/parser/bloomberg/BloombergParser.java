package com.farouk.bengharssallah.rss.parser.bloomberg;

import java.io.InputStream;
import java.text.ParseException;
import java.util.LinkedList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.parser.RSSParser;
import com.farouk.bengharssallah.rss.util.Constants;

		/** 
		 * <p>{@link BloombergParser} is a {@link RSSParser}  to extract data extracted from BLOOMBERG.com
		 * RSS feed</p>
		 * 
		 ***/

public class BloombergParser extends RSSParser {
	
	private static final String audio = " (Audio)";
	private static final String money = "money";
	private static final String bloomberg = "(Bloomberg) -- ";
	private static final String bloomberg_catherine_reports = " Bloomberg’s Catherine Cowdery reports on exchange-traded funds.";
	private static final String gmt_timezone = " GMT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";

	public BloombergParser(String feedUrl) {
		super(feedUrl);
	         }
	
	
	@Override
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
																	        	        news.setDate(formatDate(date.replace(gmt_timezone, ""), date_format));
																	        	        news.setReference(generateReference(news, Constants.BLOOMBERG));
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
	
  }
package com.farouk.bengharssallah.rss.parser.cnbc;

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
		 * <p>{@link CNBCParser} is a {@link RSSParser}  to extract data extracted from CNBC.com
		 * RSS feed</p>
		 * 
		 ***/

public class CNBCParser extends RSSParser {
	
	private static final String desc_accent = "&#039;";
	private static final String gmt_timezone = " GMT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm";
	private static final String src_ref = "3n2c";

	public CNBCParser(String feedUrl) {
		super(feedUrl);
	         }
	
	
	@Override
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
	
  }
package com.farouk.bengharssallah.rss.parser.nbc;

import java.io.InputStream;
import java.text.ParseException;
import java.util.LinkedList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.farouk.bengharssallah.rss.model.Picture;
import com.farouk.bengharssallah.rss.parser.RSSParser;
import com.farouk.bengharssallah.rss.util.Constants;

		/** 
		 * <p>{@link NBCParser} is a {@link RSSParser}  to extract data extracted from NBC.com
		 * RSS feed</p>
		 * 
		 ***/

public class NBCParser extends RSSParser {
	
	private static final String four_timezone = " -0400";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	private static final String content_width = "1200";
	private static final String content_height = "675";
	private static final String clean_desc_quote = "&quot;";
	private static final String clean_desc_br = "/><br />";
	private static final String clean_desc_br_i = "<br/><i>";
	private static final String clean_desc_photo = ".<br/><br/>Photo Credit:";
	private static final String categ_top = "top";
	private static final String categ_top_str = "top stories";
	private static final String categ_sport = "sport";
	private static final String categ_tech = "tech";

	
	public NBCParser(String feedUrl) {
		super(feedUrl);
	         }
	
	
	@Override
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
				      Picture thumbnail_picture = null;
				      Picture content_picture = null;
				      
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
							        	                                  thumbnail_picture = getPicture(event, eventReader);
							                               }
							              if(localPart.equals(Constants.MEDIA_CONTENT)){
							        	               content_picture = getPicture(event, eventReader);
							        	                          // this picture has a static height, not available in the string picture attributes
							        	               content_picture.setHeight(content_height);
							        	                          // this picture has a static width, not available in the string picture attributes
							        	               content_picture.setWidth(content_width);
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
						        	                                              news.setImage(news.getContent_picture().getSrc());
						        	                    else if(news.getThumbnail_picture() != null)
		        	                                                              news.setImage(news.getThumbnail_picture().getSrc());
												        	}
									        	        news.setReference(generateReference(news, Constants.NBC));
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
	                            } 
	       catch (ParseException e) {
			                       e.printStackTrace();
		}
	      return list;
	         }
	
		   /** 
			 * It returns a clean {@link String} description value by eliminating static parts.
			 *	@param value   {@link String}  , initial description value
			 *  @return {@link String}
			 **/
	
	
		private String cleanDescriptionValue(String value){
					          value = value.replace(clean_desc_quote, "'");
					          value =  value.substring(value.indexOf(clean_desc_br) + 8 );
					          if(value.contains(clean_desc_br_i))
					                           value = value.substring(0, value.indexOf(clean_desc_br_i));
					          if(value.contains(clean_desc_photo))
					       	               value = value.substring(0, value.indexOf(clean_desc_photo));
		                      return value;
                   }
	
}
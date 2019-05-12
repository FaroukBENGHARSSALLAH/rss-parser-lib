package com.farouk.bengharssallah.rss.parser;


import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.farouk.bengharssallah.rss.model.News;

				/** 
				 * {@link JSRSSParser} is an abstract class which integrates some APIs related to 
				 * RSS data extraction using Jsoup API. All concrete RSS feed parser must inherit 
				 * from this abstract class .
				 * 
				 ***/

public abstract class JSRSSParser {
	
	          /** The url variable is a {@link URL} to represent the current RSS feed  URL being parsed. 
	           * It should be unique during class execution as it parses an online stream content.**/
	     protected final String url;
	     
	         
	     /** 
			 * {@link JSRSSParser} constructor. It should initialize url value.
			 *	@param feedUrl   {@link String}	feed  link
			 ***/
	     
	     public JSRSSParser(String feedUrl) {
	    	     this.url =  feedUrl;
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
			 *  @return {@link Document}
			 ***/
	     
	     protected Document read() {
			    try {
			                 return Jsoup.connect(url).userAgent("chrome").parser(Parser.xmlParser()).get();
			                 } 
			    catch (Exception e) {
			                 throw new RuntimeException(e);
			                 }
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
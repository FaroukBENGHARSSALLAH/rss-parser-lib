package com.farouk.bengharssallah.rss.parser.cnbc;

import java.util.LinkedList;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.farouk.bengharssallah.rss.parser.JSRSSParser;


		/** 
		 * <p>{@link CNBCJSParser} is a {@link JSRSSParser}  to extract data extracted from CNBC.com
		 * RSS feed</p>
		 * 
		 ***/

public class CNBCJSParser extends JSRSSParser {
	
	private static final String gmt_timezone = " GMT";
	private static final String date_format = "EEE, dd MMM yyyy HH:mm";
	private static final String src_ref = "3n2c";
	private static final String desc_accent = "&#039;";

	public CNBCJSParser(String feedUrl) {
		super(feedUrl);
	         }
	
	
	@Override
	public LinkedList<CNBCNews> getNews() {
		 LinkedList<CNBCNews> list = new LinkedList<CNBCNews>();
	       try {
	    	    
		      Document doc = read();
		      for (Element e : doc.select("item")) {
		    	            CNBCNews news = new CNBCNews();
		        	        news.setTitle(e.select("title").text());
		        	        news.setCategory("retail");
		        	        news.setDescription(e.select("description").text().replace(desc_accent, "'"));
		        	        news.setDate(formatDate(e.select("pubDate").text().replace(gmt_timezone, ""), date_format));
		        	        news.setReference(generateReference(news, src_ref));
		        	        news.setLink(e.select("link").text());
				            list.add(news);
                       }
	               }
	       catch (Exception e) {
			e.printStackTrace();
		}
	      return list;
	         }
	
  }
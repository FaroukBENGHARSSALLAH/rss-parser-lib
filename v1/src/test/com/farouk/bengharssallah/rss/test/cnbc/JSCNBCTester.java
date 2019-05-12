package com.farouk.bengharssallah.rss.test.cnbc;

import java.util.LinkedList;

import com.farouk.bengharssallah.rss.parser.cnbc.CNBCJSParser;
import com.farouk.bengharssallah.rss.parser.cnbc.CNBCNews;

public class JSCNBCTester {

	public static void main(String[] args) {
		    CNBCJSParser parser = new CNBCJSParser("http://www.cnbc.com/id/10000116/device/rss/rss.html");
		    LinkedList<CNBCNews> newsList = parser.getNews();
		    for (CNBCNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category :"
		                    		  + news.getCategory() + "              -link : " + news.getLink());
		                      System.out.println("------------------------------------------------------------ \n");

		                     }

	}

}

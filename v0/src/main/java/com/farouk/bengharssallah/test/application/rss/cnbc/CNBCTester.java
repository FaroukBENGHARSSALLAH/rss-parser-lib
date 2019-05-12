package com.farouk.bengharssallah.test.application.rss.cnbc;

import java.util.LinkedList;

public class CNBCTester {

	public static void main(String[] args) {
		    CNBCParser parser = new CNBCParser("http://www.cnbc.com/id/10000116/device/rss/rss.html");
		    LinkedList<CNBCNews> newsList = parser.getNews();
		    for (CNBCNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category :"
		                    		  + news.getCategory() + "              -link : " + news.getLink());
		                      System.out.println("------------------------------------------------------------ \n");

		                     }

	}

}

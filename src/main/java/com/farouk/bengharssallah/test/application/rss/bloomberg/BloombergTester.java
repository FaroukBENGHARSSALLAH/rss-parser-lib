package com.farouk.bengharssallah.test.application.rss.bloomberg;

import java.util.LinkedList;

public class BloombergTester {

	public static void main(String[] args) {
		    BloombergParser parser = new BloombergParser("http://www.bloomberg.com/feeds/podcasts/etf_report.xml");
		    LinkedList<BloombergNews> newsList = parser.getNews();
		    for (BloombergNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -duration :"
		                    		  + news.getDuration() + "              -link : " + news.getLink());
		                      System.out.println("------------------------------------------------------------ \n");

		                     }

	}

}

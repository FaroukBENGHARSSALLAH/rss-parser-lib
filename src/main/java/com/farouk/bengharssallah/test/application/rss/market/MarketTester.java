package com.farouk.bengharssallah.test.application.rss.market;

import java.util.LinkedList;

public class MarketTester {

	public static void main(String[] args) {
		    MarketParser parser = new MarketParser("http://moneymovesmarkets.com/journal/rss.xml");
		    LinkedList<MarketNews> newsList = parser.getNews();
		    for (MarketNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title: " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category : "
		                    		  + news.getCategory() + "              -link : " + news.getLink() );
		                      System.out.println("-chart one : " + news.getChartOnePicture() + "  -chart two : " + news.getChartTwoPicture() );
		                      System.out.println("------------------------------------------------------------ \n");

		                     }

	}

}

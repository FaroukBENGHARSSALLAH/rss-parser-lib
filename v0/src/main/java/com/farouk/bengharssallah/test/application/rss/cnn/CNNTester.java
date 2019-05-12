package com.farouk.bengharssallah.test.application.rss.cnn;

import java.util.LinkedList;

public class CNNTester {

	public static void main(String[] args) {
		
		
//		    CNNParser parser = new CNNParser("http://rss.cnn.com/rss/edition_travel.rss?format=xml");
//		    LinkedList<CNNNews> newsList = parser.getNews();
//		    for (CNNNews news : newsList) {
//		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category : "
//		                    		  + news.getCategory() + "              -link : " + news.getLink() );
//		                      System.out.println("-content144 : " + news.getContent_picture_144() + "  -content169 : " + news.getContent_picture_169() + "  -content186 : " + news.getContent_picture_186() );
//		                      System.out.println("-content250 : " + news.getContent_picture_250() + "  -content300 : " + news.getContent_picture_300() + "  -content360 : " + news.getContent_picture_360() );
//		                      System.out.println("-content480 : " + news.getContent_picture_480() + "  -content552 : " + news.getContent_picture_552() + "  -content619 : " + news.getContent_picture_619() );
//		                      System.out.println("------------------------------------------------------------ \n");
//		                     }
		    
		    
		    CNNIParser iparser = new CNNIParser("http://rss.cnn.com/rss/edition_technology.rss?format=xml");
		    LinkedList<CNNINews> newsiList = iparser.getNews();
		    for (CNNINews news : newsiList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category :"
		                    		  + news.getCategory() + "              -link : " + news.getLink() );
		                      System.out.println("-content : " + news.getContent_picture() + "  -thumbnail : " + news.getThumbnail_picture());
		                      System.out.println("------------------------------------------------------------ \n");
		                     }

	}

}

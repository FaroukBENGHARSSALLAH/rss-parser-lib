package com.farouk.bengharssallah.test.application.rss.abc;

import java.util.LinkedList;

public class ABCTester {

	public static void main(String[] args) {
		
		    ABCParser parser = new ABCParser("http://www.abc.net.au/radionational/feed/2891138/rss.xml");
		    
		   //   ABCIParser iparser = new ABCIParser("http://feeds.abcnews.com/abcnews/technologyheadlines");
		    
//		    LinkedList<ABCNews> newsList = parser.getNews();
//		    for (ABCNews news : newsList) {
//		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "      -description : " + news.getDescription() + "        -date : " + news.getDate() + "               -category :"
//		                    		  + news.getCategory() + "              -link : " + news.getLink() + "                -thumbnail : " + news.getThumbnail_picture());
//		                      System.out.println("-pic627 : " + news.getContent_picture_627()  + "   -pic705 : " +  news.getContent_picture_705() +  "    -pic1210 : " + news.getContent_picture_1210());
//		                      System.out.println("-pic1253 :  " + news.getContent_picture_1253() + "   -pic1400 : " + news.getContent_picture_1400());
//		                      System.out.println("-image :  " + news.getImage());
//		                      System.out.println("------------------------------------------------------------ \n");
//
//		                     }
//
		   
		    
		    
		    
		    LinkedList<ABCINews> newsiList = iparser.getNews();
		    for (ABCINews news : newsiList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "      -description : " + news.getDescription() + "        -date : " + news.getDate() + "               -category :"
		                    		  + news.getCategory() + "              -link : " + news.getLink() + "             ");
		                      System.out.println("-pic922 : " + news.getThumbnail_picture_992()  + "   -pic608 : " +  news.getThumbnail_picture_608() +  "    -pic384 : " + news.getThumbnail_picture_384());
		                      System.out.println("-pic144 :  " + news.getThumbnail_picture_144() + "   -pic240 : " + news.getThumbnail_picture_240());
		                      System.out.println("-image :  " + news.getImage());
		                      System.out.println("------------------------------------------------------------ \n");

		                     }

	}

}

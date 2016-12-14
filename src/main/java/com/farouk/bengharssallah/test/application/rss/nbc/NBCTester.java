package com.farouk.bengharssallah.test.application.rss.nbc;

import java.util.LinkedList;

public class NBCTester {

	public static void main(String[] args) {
		    NBCParser parser = new NBCParser("http://www.nbcsandiego.com/news/tech/?rss=y&embedThumb=y&summary=y");
		    LinkedList<NBCNews> newsList = parser.getNews();
		    for (NBCNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category : "
		                    		  + news.getCategory() + "              -link : " + news.getLink() + "    -author : " + news.getAuthor());
		                      System.out.println("-thumbnail : " + news.getThumbnail_picture() + "   -content : " + news.getContent_picture() + "  -summary_pic : " + news.getPicture_summary());
		                      System.out.println("-image : " + news.getImage() );
		                      System.out.println("------------------------------------------------------------ \n");

		                     }

	}

}

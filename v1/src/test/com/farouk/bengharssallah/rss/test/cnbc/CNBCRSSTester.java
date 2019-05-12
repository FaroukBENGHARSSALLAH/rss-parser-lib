package com.farouk.bengharssallah.rss.test.cnbc;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.farouk.bengharssallah.rss.parser.cnbc.CNBCJSParser;
import com.farouk.bengharssallah.rss.parser.cnbc.CNBCNews;
import com.farouk.bengharssallah.rss.parser.cnbc.CNBCParser;

public class CNBCRSSTester {

	
	 private static CNBCParser parser;
	 private static CNBCJSParser jsparser;
	 
	    @BeforeClass
	    public static void initCalculator() {
	    	parser = new CNBCParser("http://www.cnbc.com/id/10000116/device/rss/rss.html");
	    	jsparser = new CNBCJSParser("http://www.cnbc.com/id/10000116/device/rss/rss.html");
	    }
	 
	  
	    @Before
	    public void beforeEachTest() {
	        System.out.println("This is executed before each Test");
	    }
	 
	    @After
	    public void afterEachTest() {
	        System.out.println("This is exceuted after each Test");
	    }
	    
	    @Test
	    public void testCNBCParser() {
	    	LinkedList<CNBCNews> newsList = parser.getNews();
		    for (CNBCNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category :"
		                    		  + news.getCategory() + "              -link : " + news.getLink());
		                      System.out.println("------------------------------------------------------------ \n");

		                     }
	    }
	 
	    @Test
	    public void testCNBCJSParser() {
	    	LinkedList<CNBCNews> newsList = jsparser.getNews();
		    for (CNBCNews news : newsList) {
		                      System.out.println("-ref : " + news.getReference() + "   -title : " + news.getTitle() + "   -description : " + news.getDescription() + "  -date : " + news.getDate() + "     -category :"
		                    		  + news.getCategory() + "              -link : " + news.getLink());
		                      System.out.println("------------------------------------------------------------ \n");

		                     }
	    }

}

package com.farouk.bengharssallah.rss.parser.market;

import com.farouk.bengharssallah.rss.model.News;
import com.farouk.bengharssallah.rss.model.Picture;


	/** 
	 * {@link MarketNews} is a {@link News} child to support data extracted from moneymovesmarkets.com
	 * RSS feed
	 * 
	 ***/
@SuppressWarnings("serial")
public class MarketNews extends News {
	
	private Picture chartOnePicture ;
	private Picture chartTwoPicture ;
	
	public Picture getChartOnePicture() {
		return chartOnePicture;
	}
	public void setChartOnePicture(Picture chartOnePicture) {
		this.chartOnePicture = chartOnePicture;
	}
	public Picture getChartTwoPicture() {
		return chartTwoPicture;
	}
	public void setChartTwoPicture(Picture chartTwoPicture) {
		this.chartTwoPicture = chartTwoPicture;
	}

   }



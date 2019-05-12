package com.farouk.bengharssallah.rss.parser.bloomberg;

import com.farouk.bengharssallah.rss.model.News;

		/** 
		 * {@link BloombergNews} is a {@link News} child to support data extracted from bloomberg.com
		 * RSS feed
		 * 
		 ***/

@SuppressWarnings("serial")
public class BloombergNews extends News {
	
	private String duration;

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}

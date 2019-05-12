package com.farouk.bengharssallah.rss.parser.cnn;

import com.farouk.bengharssallah.rss.model.News;
import com.farouk.bengharssallah.rss.model.Picture;

		/** 
		 * {@link CNNINews} is a {@link News} child to support data extracted from CNN.com
		 * RSS feed
		 * 
		 ***/

@SuppressWarnings("serial")
public class CNNINews extends News{

	private Picture thumbnail_picture ;
	private Picture content_picture ;
	
	public Picture getThumbnail_picture() {
		return thumbnail_picture;
	}
	public void setThumbnail_picture(Picture thumbnail_picture) {
		this.thumbnail_picture = thumbnail_picture;
	}
	public Picture getContent_picture() {
		return content_picture;
	}
	public void setContent_picture(Picture content_picture) {
		this.content_picture = content_picture;
	}

 }
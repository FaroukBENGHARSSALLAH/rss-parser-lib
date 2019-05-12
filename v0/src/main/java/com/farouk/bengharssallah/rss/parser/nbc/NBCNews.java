package com.farouk.bengharssallah.rss.parser.nbc;

import com.farouk.bengharssallah.rss.model.News;
import com.farouk.bengharssallah.rss.model.Picture;


		/** 
		 * {@link NBCNews} is a {@link News} child to support data extracted from NBC.com
		 * RSS feed
		 * 
		 ***/

@SuppressWarnings("serial")
public class NBCNews extends News {
	
	private String author;
	private String picture_summary;
	private Picture thumbnail_picture ;
	private Picture content_picture ;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPicture_summary() {
		return picture_summary;
	}

	public void setPicture_summary(String picture_summary) {
		this.picture_summary = picture_summary;
	}

   public Picture getThumbnail_picture() {
	    return this.thumbnail_picture;
	 }
		  
   public void setThumbnail_picture(Picture thumbnail_picture){
	    this.thumbnail_picture = thumbnail_picture;
     }
  
   public Picture getContent_picture() {
	    return this.content_picture;
	 }
	  
   public void setContent_picture(Picture content_picture){
		this.content_picture = content_picture;
	 }

}

package com.farouk.bengharssallah.rss.parser.abc;

import com.farouk.bengharssallah.rss.model.News;
import com.farouk.bengharssallah.rss.model.Picture;


		/** 
		 * {@link ABCNews} is a {@link News} child to support data extracted from ABC.com
		 * RSS feed
		 * 
		 ***/

@SuppressWarnings("serial")
public class ABCNews extends News {
	
	private Picture content_picture_1210 ;
	private Picture content_picture_705 ;
	private Picture content_picture_627 ;
	private Picture content_picture_1253 ;
	private Picture content_picture_1400 ;
	private Picture thumbnail_picture ;
	
	 public Picture getContent_picture_1210() {
		    return this.content_picture_1210;
		 }
		  
	  public void setContent_picture_1210(Picture content_picture_1210){
			this.content_picture_1210 = content_picture_1210;
		 }
	  
	  public Picture getContent_picture_705() {
		    return this.content_picture_705;
		 }
		  
	  public void setContent_picture_705(Picture content_picture_705){
			this.content_picture_705 = content_picture_705;
		 }
	  
	  public Picture getContent_picture_627() {
		    return this.content_picture_627;
		 }
		  
	  public void setContent_picture_627(Picture content_picture_627){
			this.content_picture_627 = content_picture_627;
		 }
	  
	  public Picture getContent_picture_1253() {
		    return this.content_picture_1253;
		 }
		  
	  public void setContent_picture_1253(Picture content_picture_1253){
			this.content_picture_1253 = content_picture_1253;
		 }
	  
	  public Picture getContent_picture_1400() {
		    return this.content_picture_1400;
		 }
		  
	  public void setContent_picture_1400(Picture content_picture_1400){
			this.content_picture_1400 = content_picture_1400;
		 }

	   public Picture getThumbnail_picture() {
		    return this.thumbnail_picture;
		 }
		  
	  public void setThumbnail_picture(Picture thumbnail_picture){
		    this.thumbnail_picture = thumbnail_picture;
	     }
	  
   }
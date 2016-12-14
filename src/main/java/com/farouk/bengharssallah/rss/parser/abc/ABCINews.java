package com.farouk.bengharssallah.rss.parser.abc;

import com.farouk.bengharssallah.rss.model.News;
import com.farouk.bengharssallah.rss.model.Picture;


		/** 
		 * {@link ABCINews} is a a type of   {@link ABCNews}   to support data extracted from ABC.com
		 * RSS feed
		 * 
		 ***/

@SuppressWarnings("serial")
public class ABCINews extends News {
	
	private Picture thumbnail_picture_992 ;
	private Picture thumbnail_picture_608 ;
	private Picture thumbnail_picture_384 ;
	private Picture thumbnail_picture_240 ;
	private Picture thumbnail_picture_144 ;
	
	
	public Picture getThumbnail_picture_992() {
		return thumbnail_picture_992;
	}
	public void setThumbnail_picture_992(Picture thumbnail_picture_992) {
		this.thumbnail_picture_992 = thumbnail_picture_992;
	}
	public Picture getThumbnail_picture_608() {
		return thumbnail_picture_608;
	}
	public void setThumbnail_picture_608(Picture thumbnail_picture_608) {
		this.thumbnail_picture_608 = thumbnail_picture_608;
	}
	public Picture getThumbnail_picture_384() {
		return thumbnail_picture_384;
	}
	public void setThumbnail_picture_384(Picture thumbnail_picture_384) {
		this.thumbnail_picture_384 = thumbnail_picture_384;
	}
	public Picture getThumbnail_picture_240() {
		return thumbnail_picture_240;
	}
	public void setThumbnail_picture_240(Picture thumbnail_picture_240) {
		this.thumbnail_picture_240 = thumbnail_picture_240;
	}
	public Picture getThumbnail_picture_144() {
		return thumbnail_picture_144;
	}
	public void setThumbnail_picture_144(Picture thumbnail_picture_144) {
		this.thumbnail_picture_144 = thumbnail_picture_144;
	}
	 
   }
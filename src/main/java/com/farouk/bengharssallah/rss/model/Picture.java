package com.farouk.bengharssallah.rss.model;

import java.io.Serializable;

			/**
			 *  {@link Picture} class  encapsulate a single data item picture existed in the RSS.
			 * Some RSS feed can contain pictures data with default height and width attributes.
			 * 
			 ***/

@SuppressWarnings("serial")
public class Picture implements Serializable {
	
	 /** item RSS data src, source link of the picture **/
	private String src;
	
	 /** item RSS data Height  **/
	private String height;
	
	 /** item RSS data Width **/
	private String width;
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
}

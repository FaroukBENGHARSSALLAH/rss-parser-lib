package com.farouk.bengharssallah.rss.model;

import java.io.Serializable;

              /** 
               * {@link News} is a model class to integrate a single data item existed in the RSS.
               * All concrete RSS feed must inherit from this abstract class
               * as it already has default attributes like title, description and date.
               * 
               ***/

@SuppressWarnings("serial")
public abstract class News implements Serializable  {

	  /** item RSS data reference **/
	private String reference ;
		
	  /** item RSS data Title **/
	private String title ;
	
	  /** item RSS data Category **/
	private String category ;
	
	  /** item RSS data Description **/
	private String description;
	
	  /** item RSS data Date **/
	private String date;
	
	  /** item RSS data Link **/
	private String link;
	
	  /** item RSS data image online source path **/
	private String image;
	
	public String getReference() {
	    return this.reference;
	  }
	  
	  public void setReference(String reference){
		    this.reference = reference;
	  }
	  
	  public String getTitle() {
	    return this.title;
	  }
	  
	  public void setTitle(String title){
		    this.title = title;
	  }
	  
	  public String getCategory() {
		    return this.category;
		  }
		  
	  public void setCategory(String category){
			this.category = category;
		  }
	  
	  public String getDescription() {
	    return this.description;
	  }
	  
	  public void setDescription(String description){
		  this.description = description;
	  }

	  public String getLink() {
	    return this.link;
	  }
	  
	  public void setLink(String Link){
		  this.link = Link;
	  }

	  public String getDate() {
	    return this.date;
	  }
	  
	  public void setDate(String date){
		  this.date = date;
	  }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	} 
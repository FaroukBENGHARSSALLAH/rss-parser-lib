package com.farouk.bengharssallah.test.application.rss.cnn;


public class CNNINews {

	private String reference;
	private String title ;
	private String category ;
	private String description;
	private String date;
	private String link;
	private String image;
	private String thumbnail_picture ;
	private String content_picture ;

	public String getReference() {
	    return this.reference;
	  }
	  
	  public void setReference(String reference){
		    this.reference = reference;
	  }
	public String getThumbnail_picture() {
		return thumbnail_picture;
	}

	public void setThumbnail_picture(String thumbnail_picture) {
		this.thumbnail_picture = thumbnail_picture;
	}

	public String getContent_picture() {
		return content_picture;
	}

	public void setContent_picture(String content_picture) {
		this.content_picture = content_picture;
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
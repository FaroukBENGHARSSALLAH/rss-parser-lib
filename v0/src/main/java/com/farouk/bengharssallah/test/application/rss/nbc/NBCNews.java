package com.farouk.bengharssallah.test.application.rss.nbc;


public class NBCNews {
	
	private String reference;
	private String author;
	private String title ;
	private String category ;
	private String description;
	private String date;
	private String link;
	private String image;
	private String picture_summary;
	private String thumbnail_picture ;
	private String content_picture ;

    public String getReference() {
	    return this.reference;
	}
	  
	public void setReference(String reference){
		    this.reference = reference;
	}
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
	    return this.title;
	  }
	  
	  public String getPicture_summary() {
		return picture_summary;
	}

	public void setPicture_summary(String picture_summary) {
		this.picture_summary = picture_summary;
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

	  public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDate() {
	    return this.date;
	  }
	  
	  public void setDate(String date){
		  this.date = date;
	  }

   }
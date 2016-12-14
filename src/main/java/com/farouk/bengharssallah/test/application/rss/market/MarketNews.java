package com.farouk.bengharssallah.test.application.rss.market;


public class MarketNews {

	private String reference;
	private String title ;
	private String category ;
	private String description;
	private String date;
	private String link;
	private String image;
	private String chartOnePicture ;
	private String chartTwoPicture ;

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

	public String getChartOnePicture() {
		return chartOnePicture;
	  }

	public void setChartOnePicture(String chartOnePicture) {
		this.chartOnePicture = chartOnePicture;
	  }

	public String getChartTwoPicture() {
		return chartTwoPicture;
	  }

	public void setChartTwoPicture(String chartTwoPicture) {
		this.chartTwoPicture = chartTwoPicture;
	  }

   }
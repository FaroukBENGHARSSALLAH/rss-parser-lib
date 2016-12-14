package com.farouk.bengharssallah.rss.util;

public class RSSTools {
	


    
     public static String getRSSSourceCategory(String reference){
    	 
    	                            String source = null;
    	                            String category = null;
    	                            
    	                            if(reference.contains(Constants.ABCI)){
    	                            	    source = "ABCI";
                                                      }
    	                            else if(reference.contains(Constants.ABC)){
    	                            	     source = "ABC";
    	                                               }
    	                            else if(reference.contains(Constants.BLOOMBERG)){
    	                            	     source = "BLOOMBERG";
                                                       }
    	                            else if(reference.contains(Constants.CNBC)){
    	                            	    source = "CNBC";
                                                        }
    	                            else if(reference.contains(Constants.CNNI)){
    	                            	     source = "CNNI";
                                                       }
    	                            else if(reference.contains(Constants.CNN)){
    	                            	      source = "CNN";
                                                       }
    	                            else if(reference.contains(Constants.MARKET)){
    	                            	      source = "MARKET";
                                                         }
    	                            else if(reference.contains(Constants.NBC)){
    	                            	       source  = "NBC";
                                                          }
    	                            
    	                             if(reference.contains(Constants.TOP_STORIES)){
    	                            	         category =  "top";
                                                          }
					                  else if(reference.contains(Constants.WORLD)){
					                	         category = "world";
					                                     }
					                  else if(reference.contains(Constants.SPORT)){
					                	          category =  "sport";
					                                     }
					                  else if(reference.contains(Constants.MUSIC)){
					                	          category =  "music";
					                                     }
					                  else if(reference.contains(Constants.ARTS)){
					                	          category =  "arts";
					                                     }
					                  else if(reference.contains(Constants.ENTERTAINMENT)){
					                	          category =  "entertainment";
					                                      }
					                  else if(reference.contains(Constants.TECHNOLOGY)){
					                	          category =  "technology";
					                                       }
					                  else if(reference.contains(Constants.TRAVEL)){
					                	           category = "travel";
		                                       }
					                  else if(reference.contains(Constants.MONEY) || reference.contains(Constants.BUSINESS)){
					                	           category =  "money";
                                               }
    	                            
    	                            return source + "~" + category;
                              }
    
               }


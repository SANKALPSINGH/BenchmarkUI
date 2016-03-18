package com.bsb.hike.qa.jsonbuilder;

import org.apache.commons.lang.RandomStringUtils;

import com.google.gson.JsonObject;

public class StatusUpdateJson {

	/*
	 * creating a json for setting text for status update
	 */
	public String statusUpdate(){
		String suMessage = "hey"+RandomStringUtils.randomNumeric(3);
		int mood = 1;
		JsonObject postData = new JsonObject();
		postData.addProperty("status-message", suMessage);
		postData.addProperty("mood", mood);
		postData.addProperty("fb", false);
		postData.addProperty("twitter", false);
		postData.addProperty("timeofday", 2);  
		return postData.toString();
	}
	
	/*
	 * creating a json for setting mood for status update
	 */
	public String statusUpdate(String suMessage,int mood)
	{
		JsonObject postData=new JsonObject();
		postData.addProperty("status-message", suMessage);
		postData.addProperty("mood", mood);
		postData.addProperty("fb", false);
		postData.addProperty("twitter", false);
		postData.addProperty("timeofday",2);  
		return postData.toString();
	}
}

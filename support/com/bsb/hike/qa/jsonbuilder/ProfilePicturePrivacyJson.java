package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class ProfilePicturePrivacyJson {
	
	/*
	 * creating a json for toggling profile picture privacy to "show to only favorites"
	 */
	public String privacyOn(){
		JsonObject jsonDataObject = new JsonObject();
		jsonDataObject.addProperty("avatar", "2");
		JsonObject jsonData = new JsonObject();
		jsonData.add("d", jsonDataObject);
		jsonData.addProperty("t", "ac");
		
		return jsonData.toString();
		
	}
	
	/*
	 * creating a json for toggling picture privacy to "show to all"
	 */
	public String privacyOff(){
		JsonObject jsonDataObject = new JsonObject();
		jsonDataObject.addProperty("avatar", "1");
		JsonObject jsonData = new JsonObject();
		jsonData.add("d", jsonDataObject);
		jsonData.addProperty("t", "ac");
		
		return jsonData.toString();
		
	}

}

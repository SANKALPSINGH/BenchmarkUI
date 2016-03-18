package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class MuteUnmuteStatusNotificationJson {
	
	public String mute(){
		JsonObject jsonDataObject = new JsonObject();
		jsonDataObject.addProperty("i", "1409226006515");
		jsonDataObject.addProperty("pushsu", "-1");
		JsonObject jsonData = new JsonObject();
		jsonData.add("d", jsonDataObject);
		jsonData.addProperty("t", "ac");
		
		return jsonData.toString();
		
	}
	
	public String unmute(){
		JsonObject jsonDataObject = new JsonObject();
		jsonDataObject.addProperty("i", "1409226006515");
		jsonDataObject.addProperty("pushsu", "0");
		JsonObject jsonData = new JsonObject();
		jsonData.add("d", jsonDataObject);
		jsonData.addProperty("t", "ac");
		
		return jsonData.toString();
		
	}

}

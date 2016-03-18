package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class LastSeenPrivacyJson {
			
		public String LastSeenPrivacyON(){
			JsonObject jsonDataObject = new JsonObject();
			jsonDataObject.addProperty("i", "1409226006515");
			jsonDataObject.addProperty("lastseen", "false");
			JsonObject jsonData = new JsonObject();
			jsonData.add("d", jsonDataObject);
			jsonData.addProperty("t", "ac");
			
			return jsonData.toString();
			
		}
		
		public String LastSeenPrivacyOFF(){
			JsonObject jsonDataObject = new JsonObject();
			jsonDataObject.addProperty("i", "1409226006515");
			jsonDataObject.addProperty("lastseen", "true");
			JsonObject jsonData = new JsonObject();
			jsonData.add("d", jsonDataObject);
			jsonData.addProperty("t", "ac");
			
			return jsonData.toString();
			
		}

}

package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class FriendJson {

	/*
	 * creating json for adding a contact as favorite
	 */
	
	public String addFavorite(String friendToAdd){
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("id", friendToAdd);           
        JsonObject jsonData = new JsonObject();
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "af");
        return jsonData.toString();
	}
	
	/*
	 * creating a json for removing contact from favorite list
	 */
	public String removeFavorite(String msisdnToRemove){
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("id", msisdnToRemove);           
        JsonObject jsonData = new JsonObject();
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "rf");
        return jsonData.toString(); 
	}
	
	/*
	 * creating a json for adding contacts to favorite list during FTUE
	 */
	public String addFavoriteDuringFTUE(String friendToAdd){
        JsonObject jsonDataObj = new JsonObject();
        JsonObject md = new JsonObject();
        md.addProperty("screen", "ftue");
        jsonDataObj.addProperty("id", friendToAdd);     
        jsonDataObj.add("md", md);  
        JsonObject jsonData = new JsonObject();
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "af");
        return jsonData.toString();
	}
}

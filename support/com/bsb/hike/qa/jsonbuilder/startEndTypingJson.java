package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

/**
 * @author amita
 *
 */
public class startEndTypingJson {

	/*
	 * creating json for start typing 
	 */
	public static String startTyping(String msisdnSender, String msisdnReceiver){
		 JsonObject jsonData = new JsonObject();
         jsonData.addProperty("t", "st");
         jsonData.addProperty("f", msisdnSender);
         jsonData.addProperty("to", msisdnReceiver);
         jsonData.addProperty("i", System.currentTimeMillis());
        return jsonData.toString();
	}
	

	/*
	 * creating json for end typing 
	 */
	public static String endTyping(String msisdnSender, String msisdnReceiver){
		 JsonObject jsonData = new JsonObject();
         jsonData.addProperty("t", "et");
         jsonData.addProperty("f", msisdnSender);
         jsonData.addProperty("to", msisdnReceiver);
         jsonData.addProperty("i", System.currentTimeMillis());
        return jsonData.toString();
	}
	
}

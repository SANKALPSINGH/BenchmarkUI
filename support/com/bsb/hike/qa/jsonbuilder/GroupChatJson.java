package com.bsb.hike.qa.jsonbuilder;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GroupChatJson {

	/*
	 * creating a json for creating group chat with group name
	 */
	public static String gcjWithGroupName(String groupName , String groupId , String msisdnCreator , List<String> jsonMemberstoAdd){
		JsonObject jsonGCJ = new JsonObject();
		try {
            JsonArray groupMembers = new JsonArray();
            for (String msisdn : jsonMemberstoAdd) {
            	JsonObject nameAndMsisdn = new JsonObject();
                nameAndMsisdn.addProperty("msisdn", msisdn) ;
                nameAndMsisdn.addProperty("name", msisdn);
                groupMembers.add(nameAndMsisdn);
			}
            
            JsonObject groupNameJson = new JsonObject();
            groupNameJson.addProperty("name", groupName);            
            jsonGCJ.addProperty("to", groupId);
            jsonGCJ.addProperty("f", msisdnCreator);
            jsonGCJ.add("d", groupMembers);
            jsonGCJ.addProperty("t", "gcj");
            jsonGCJ.add("md", groupNameJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonGCJ.toString();
	}
	
	/*
	 * creating a json for creating group chat without name
	 */
	public static String gcjWithoutGroupName(String groupName , String groupId , String msisdnCreator , List<String> jsonMemberstoAdd){
		JsonObject jsonGCJ = new JsonObject();
		try {
            JsonArray groupMembers = new JsonArray();
            for (String msisdn : jsonMemberstoAdd) {
            	JsonObject nameAndMsisdn = new JsonObject();
                nameAndMsisdn.addProperty("msisdn", msisdn) ;
                nameAndMsisdn.addProperty("name", msisdn);
                groupMembers.add(nameAndMsisdn);
			}
                       
            jsonGCJ.addProperty("to", groupId);
            jsonGCJ.addProperty("f", msisdnCreator);
            jsonGCJ.add("d", groupMembers);
            jsonGCJ.addProperty("t", "gcj");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonGCJ.toString();
	}
	
	
	public static String addMembersJson(String groupId , List<String> jsonMemberstoAdd){
		JsonObject jsonGCJ = new JsonObject();
		try {	
			JsonArray groupMembers = new JsonArray();
		      for (String msisdn : jsonMemberstoAdd) {
		      	JsonObject nameAndMsisdn = new JsonObject();
		          nameAndMsisdn.addProperty("msisdn", msisdn) ;
		          nameAndMsisdn.addProperty("name", msisdn);
		          groupMembers.add(nameAndMsisdn);
				}
			jsonGCJ.addProperty("to", groupId);
			jsonGCJ.add("d", groupMembers);
			jsonGCJ.addProperty("i", System.currentTimeMillis());
			jsonGCJ.addProperty("t", "gcj");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonGCJ.toString();
	}
	  
	/*
	 * creating a json for sending group chat message
	 */
	public static String gcMessage(String groupId){
		JsonObject jsonData = new JsonObject();
		try {
			JsonObject jsonDataObj = new JsonObject();
			long ts = System.currentTimeMillis();
            String message = "Hello ! I am the group intiator";
            jsonDataObj.addProperty("ts", ts); 
            jsonDataObj.addProperty("hm", message);
            jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));            
            jsonData.addProperty("to", groupId);
            jsonData.add("d", jsonDataObj);
            jsonData.addProperty("t", "m");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData.toString();
	}
	
	/*
	 * creating a json when any GC participant leaves group
	 */
	public static String gclJson(String groupId){
		JsonObject json = new JsonObject();
		try {
			//{"to":"U7vUqeV-iwwwQoWX:1405407182746","i":"1405413840","t":"gcl"}
			json.addProperty("to", groupId);
			json.addProperty("t", "gcl");
			json.addProperty("i", System.currentTimeMillis());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	/*
	 * creating a json when group owner kicks any participants
	 */
	public static String gckJson(String groupId , String msisdnToKick){
		JSONObject json = new JSONObject();
		try {
			// {"to":"U8PIveV-izip9WUY:1405408265259","d":{"i":"1405414342414","msisdns":["+919711118690"]},"t":"gck"}
			long ts = System.currentTimeMillis();
			JSONArray msisdns = new JSONArray();
			msisdns.add(msisdnToKick);
			
			JSONObject data = new JSONObject();
			data.put("i", Long.toString(ts));
			data.put("msisdns", msisdns);
			
			
			json.put("d", data);
			json.put("to", groupId);
			json.put("t", "gck");
			System.out.println("GCK:  " + json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	/*
	 * creating a json when someone pins some message in group chat
	 */
	public static String setPINJson(String groupId , String msg){
		JsonObject json = new JsonObject();
		try {
			  JsonObject pin = new JsonObject();
			  pin.addProperty("pin", 1);
			  
			  JsonObject data = new JsonObject();
			  data.addProperty("ts", System.currentTimeMillis());
			  data.add("md", pin);
			  data.addProperty("i", 10);
			  data.addProperty("hm", msg);
			  
			  json.addProperty("to", groupId);
			  json.add("d", data);
			  json.addProperty("t", "m");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	/*
	 * Creating a json to mute/unmute a group
	 */
	public static String muteUnmuteJson (String groupId , String msg)
	{
		 JsonObject json = new JsonObject();
		 //{"d":{"id":"U9X7uuV-izIvkxFU:428397773","i":"1409554820"},"t":"mute"}
		try {
			  json.addProperty("t", msg);
			  JsonObject data = new JsonObject();
			  data.addProperty("i", System.currentTimeMillis());
			  data.addProperty("id", groupId);
			  json.add("d", data);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	/*
	 * Creating Json For setting group name
	 */
	public static String editGroupNameJson(String name)
	{
		JsonObject postData = new JsonObject();
        postData.addProperty("name", name);
        return postData.toString();     
	}
}

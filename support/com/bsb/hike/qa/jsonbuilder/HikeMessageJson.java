package com.bsb.hike.qa.jsonbuilder;

import org.apache.commons.lang.RandomStringUtils;

import com.bsb.hike.util.UTFConversion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HikeMessageJson {

	/*
	 * creating a json for sending hike to hike message
	 */
	public static String hikeMessage(String msisdnReceiver , String message){
        long ts = System.currentTimeMillis();
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", msisdnReceiver);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        return jsonData.toString();
	}
	
	/*
	 * creating a json for sending hike to sms message
	 */
	public static String smsMessage(String msisdnReceiver){
        long ts = System.currentTimeMillis();
        String message = "sms-automated#" + RandomStringUtils.randomNumeric(8);
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("sm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", msisdnReceiver);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        return jsonData.toString();
	}
	
	/*
	 * creating a json for sending hike invite
	 */
	public static String hikeSmsInvite(String msisdnReceiver){
        long ts = System.currentTimeMillis();
        //String message = "hike-automated#" + RandomStringUtils.randomNumeric(8);
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(4));
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", msisdnReceiver);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "i");
        return jsonData.toString();
	}
	
	/*
	 * creating a json for sending stickers to hike user
	 */
	public static String hikeSticker(String msisdnReceiver , String categoryId , String stickerId){
		//{"d":{"hm":"Sticker","i":"64","ts":0,"md":{"catId":"humanoid","stId":"001_love1.png"}},"to":"+917777771885","st":"stk","t":"m"}

		JsonObject mdObj = new JsonObject();
        mdObj.addProperty("catId", categoryId); 
        mdObj.addProperty("stId", stickerId);
                 
        long ts = System.currentTimeMillis();
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", "Sticker");
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(5));
        jsonDataObj.add("md", mdObj);
                   
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", msisdnReceiver);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("st", "stk");
        jsonData.addProperty("t", "m");
        return jsonData.toString();
	}
	
	
	/*
	 * creating a json for sending nudge to hike user
	 */
	public static String hikeNudge(String msisdnReceiver){    
        

         
          long ts = System.currentTimeMillis();
          String uniqueId = RandomStringUtils.randomNumeric(6);
          JsonObject jsonDataObj = new JsonObject();
          jsonDataObj.addProperty("poke", "true");
          jsonDataObj.addProperty("hm", "Nudge!");
          jsonDataObj.addProperty("ts", ts); 
          jsonDataObj.addProperty("i", uniqueId);
         
                     
          JsonObject jsonData = new JsonObject();
          jsonData.addProperty("to", msisdnReceiver);
          jsonData.add("d", jsonDataObj);
          jsonData.addProperty("t", "m");
          return jsonData.toString();
       
       
	}
	
	//json message with numeric characters - Yash
	
	public static String hikeMessageWithNumericCharacters(String destinationMsisdn){
        long ts = System.currentTimeMillis();
        String message = "hike-automated#" + RandomStringUtils.randomNumeric(8);
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        return jsonData.toString();
	}

	/*
	 * creating a json for sending really long message
	 */
	public static String hikeMessageWithLongString(String destinationMsisdn){

	   long ts = System.currentTimeMillis();
       String message = "messageWithLongString#" + RandomStringUtils.randomAlphabetic(8000);
       JsonObject jsonDataObj = new JsonObject();
       jsonDataObj.addProperty("ts", ts); 
       jsonDataObj.addProperty("hm", message);
       jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));        
       JsonObject jsonData = new JsonObject();
       jsonData.addProperty("to", destinationMsisdn);
       jsonData.add("d", jsonDataObj);
       jsonData.addProperty("t", "m");
	
       return jsonData.toString();
	
	}

	/*
	 * creating a json for sending hike messages containing special characters
	 */
	public static String hikeMessageWithSpecialCharacters(String destinationMsisdn){

		long ts = System.currentTimeMillis();
        String message = "messageWithSpecialCharacter#" + "!@#$%^&*()_+=-~`[];':,.<>?/";
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));         
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
		
	    return jsonData.toString();
		
	}
	
	/*
	 * creating a json for sending hike messages containing smileys
	 */
	public static String hikeSmileyMessage(String destinationMsisdn){
		
		long ts = System.currentTimeMillis();
        String message = "messageWithSpecialCharacter#" + "(chips)";
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));         
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        
		return jsonData.toString();
        
	}
	
	/*
	 * creating a json for sending hike stickers
	 */

	public static String hikeStickerMessage(String destinationMsisdn){
		
		long ts = System.currentTimeMillis();
        String message = "Sticker";
        String stickerId = "003_teasing.png";
        String categoryId =	"humanoid";
        JsonObject jsonStickerData = new JsonObject();
        jsonStickerData.addProperty("stId", stickerId);
        jsonStickerData.addProperty("catId", categoryId);
        
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.add("md", jsonStickerData);
        jsonDataObj.addProperty("hm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));         
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        jsonData.addProperty("st", "stk");
		
		return jsonData.toString();
		
	}
	
	public static String hike2hikeMessage(String userMessage, String destinationMsisdn){
        long ts = System.currentTimeMillis();
//        String message = "hike-automated#" + ;
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", UTFConversion.convert(userMessage));
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        return jsonData.toString();
	}
	
	//json for creating message with sticker - Yash

	public static String hikeStickerMessage(String stickerId, String categoryId, String destinationMsisdn){
		
		long ts = System.currentTimeMillis();
        String message = "Sticker";

        JsonObject jsonStickerData = new JsonObject();
        jsonStickerData.addProperty("stId", stickerId);
        jsonStickerData.addProperty("catId", categoryId);
        
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.add("md", jsonStickerData);
        jsonDataObj.addProperty("hm", message);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));         
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
        jsonData.addProperty("st", "stk");
		
		return jsonData.toString();
		
	}
	
	//json for creating message with attachment - Yash 
	
	public static String hikeMessageWithFile(String contentType, String thumbNail, String fileSize, String md5Original, String fileKey, String nameOfFile, String is_converted, String destinationMsisdn){
		
		long ts = System.currentTimeMillis();
		
		JsonObject arr = new JsonObject();
        arr.addProperty("ct", contentType);
        arr.addProperty("tn",thumbNail);
        arr.addProperty("fs", fileSize);
        arr.addProperty("md5_original", md5Original);
        arr.addProperty("fk", fileKey);
        arr.addProperty("fn", nameOfFile);
        arr.addProperty("is_converted", is_converted);
               
        JsonArray array = new JsonArray();
        array.add(arr);

        JsonObject jsonMedia = new JsonObject();
        jsonMedia.add("files", array);

        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.add("md", jsonMedia);
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));
        jsonDataObj.addProperty("hm", "");

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", jsonDataObj);
        jsonData.addProperty("t", "m");
		
		return jsonData.toString();
		
	}

	/*
	 * creating a json for sending sms message
	 */
	public static String smsMessageWithLongString(String destinationMsisdn){

		 long ts = System.currentTimeMillis();
	        String message = "sms-automated#" + RandomStringUtils.randomNumeric(160);
	        JsonObject jsonDataObj = new JsonObject();
	        jsonDataObj.addProperty("ts", ts); 
	        jsonDataObj.addProperty("sm", message);
	        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(2));
	        
	        JsonObject jsonData = new JsonObject();
	        jsonData.addProperty("to", destinationMsisdn);
	        jsonData.add("d", jsonDataObj);
	        jsonData.addProperty("t", "m");
	        return jsonData.toString();
	
	}
	
	/*
	 * creating a json for sending multiple H2O message
	 */
	public static String multipleHike2OfflineMessage(String destinationMsisdn){
		
		    long ts = System.currentTimeMillis();
	        JsonObject jsonDataObj = new JsonObject();
	        jsonDataObj.addProperty("ts", ts); 
	        jsonDataObj.addProperty("hm", "");
	        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(3));
	        
	        ts = System.currentTimeMillis();
	        JsonObject jsonDataObj1 = new JsonObject();
	        jsonDataObj1.addProperty("ts", ts); 
	        jsonDataObj1.addProperty("hm", "");
	        jsonDataObj1.addProperty("i", RandomStringUtils.randomNumeric(3));
	       
	        JsonArray ja=new JsonArray();
	        ja.add(jsonDataObj);
	        ja.add(jsonDataObj1);
	        
	        JsonObject data=new JsonObject();
	        data.addProperty("c", 2);
	        data.addProperty("i",RandomStringUtils.randomNumeric(3));
	        data.add("m",ja);
	        
	        JsonObject jsonData = new JsonObject();
	        jsonData.addProperty("to", destinationMsisdn);
	        jsonData.add("d", data);
	        jsonData.addProperty("t", "fsms");
	        return jsonData.toString();
	}
	
	/*
	 * creating a json for sending H2O message
	 */
	public static String multipleHike2OfflineMessage(String destinationMsisdn,String message){
		
		    long ts = System.currentTimeMillis();
	        JsonObject jsonDataObj = new JsonObject();
	        jsonDataObj.addProperty("ts", ts); 
	        jsonDataObj.addProperty("hm", message);
	        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(3));
	        
	        ts = System.currentTimeMillis();
	        JsonObject jsonDataObj1 = new JsonObject();
	        jsonDataObj1.addProperty("ts", ts); 
	        jsonDataObj1.addProperty("hm", message+"-sent via hike free sms");
	        jsonDataObj1.addProperty("i", RandomStringUtils.randomNumeric(3));
	       
	        JsonArray ja=new JsonArray();
	        ja.add(jsonDataObj);
	        ja.add(jsonDataObj1);
	        
	        JsonObject data=new JsonObject();
	        data.addProperty("c", 2);
	        data.addProperty("i",RandomStringUtils.randomNumeric(3));
	        data.add("m",ja);
	        
	        JsonObject jsonData = new JsonObject();
	        jsonData.addProperty("to", destinationMsisdn);
	        jsonData.add("d", data);
	        jsonData.addProperty("t", "fsms");
	        return jsonData.toString();
	}
	
	public static String hike2OfflineMessage(String destinationMsisdn,String message){
		
	    long ts = System.currentTimeMillis();
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", message+"-sent via hike free sms");
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(3));
        JsonArray ja=new JsonArray();
        ja.add(jsonDataObj);
       
        JsonObject data=new JsonObject();
        data.addProperty("c", 1);
        data.addProperty("i",RandomStringUtils.randomNumeric(3));
        data.add("m",ja);
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", data);
        jsonData.addProperty("t", "fsms");
        return jsonData.toString();
}
	
public static String hike2OfflineMessage(String destinationMsisdn){
		
	    long ts = System.currentTimeMillis();
        JsonObject jsonDataObj = new JsonObject();
        jsonDataObj.addProperty("ts", ts); 
        jsonDataObj.addProperty("hm", "");
        jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(3));
        JsonArray ja=new JsonArray();
        ja.add(jsonDataObj);
       
        JsonObject data=new JsonObject();
        data.addProperty("c", 1);
        data.addProperty("i",RandomStringUtils.randomNumeric(3));
        data.add("m",ja);
        
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("to", destinationMsisdn);
        jsonData.add("d", data);
        jsonData.addProperty("t", "fsms");
        return jsonData.toString();
}

	/*
	 * Json for sending multiple invites
	 */
	public static String hikeSmsMultipleInvite(String msisdns[])
	{
		long ts = System.currentTimeMillis();
		JsonObject jsonDataObj = new JsonObject();
		jsonDataObj.addProperty("ts", ts); 
		jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(4));
		JsonArray listJson=new JsonArray();
		JsonObject jsonData = new JsonObject();
		JsonElement msisdn; 
		for(String l:msisdns)
		{
			msisdn= (JsonElement)new JsonParser().parse(l);
			listJson.add(msisdn);
		}
		jsonData.addProperty("t", "mi");
		jsonDataObj.add("list",listJson );
		jsonData.add("d", jsonDataObj);
		System.out.println( jsonData.toString());
		return jsonData.toString();
	}
	
	/*
	 * Json for sending multiple invites during FTUE
	 */
	public static String hikeSmsMultipleInviteDuringFTUE(String msisdns[])
	{
		long ts = System.currentTimeMillis();
		JsonObject jsonDataObj = new JsonObject();
		jsonDataObj.addProperty("ts", ts); 
		jsonDataObj.addProperty("i", RandomStringUtils.randomNumeric(4));
		JsonArray listJson=new JsonArray();
		JsonObject jsonData = new JsonObject();
		JsonElement msisdn; 
		for(String l:msisdns)
		{
			msisdn= (JsonElement)new JsonParser().parse(l);
			listJson.add(msisdn);
		}
		JsonObject innerJson=new JsonObject();
		innerJson.addProperty("screen", "ftue");
		jsonDataObj.add("md", innerJson);
		jsonData.addProperty("t", "mi");
		jsonDataObj.add("list",listJson );
		jsonData.add("d", jsonDataObj);
		System.out.println( jsonData.toString());
		return jsonData.toString();
	}
}

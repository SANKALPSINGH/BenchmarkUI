package com.bsb.hike.qa.jsonbuilder;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StealthJson {

	/*
	 * creating a json for enabling stealth mode
	 */
	@SuppressWarnings("unchecked")
	public static String enableStealthJson(String[] enableStealthList){
		JSONObject json = new JSONObject();
        
        JSONArray en = new JSONArray();
        for (String msisdn : enableStealthList) {
        	 en.add(msisdn);
		}
        
        JSONObject data = new JSONObject();
        data.put("di", new ArrayList<String>());
        data.put("en", en);
        
        json.put("d", data);
        json.put("t", "stlth");
        return json.toString();
	}
	
	/*
	 * creating a json for disabling stealth mode
	 */
	@SuppressWarnings("unchecked")
	public static String disableStealthJson(String[] disableStealthList){
		JSONObject json = new JSONObject();
        
        JSONArray di = new JSONArray();
        for (String msisdn : disableStealthList) {
        	 di.add(msisdn);
		}
        
        JSONObject data = new JSONObject();
        data.put("di", di);
        data.put("en", new ArrayList<String>());
        
        json.put("d", data);
        json.put("t", "stlth");
        return json.toString();
	}
}

package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class CBGJson {
	
	/*
	 * creating json for changing background theme with specific background id
	 */
	public static String getCBGJson(String bg_id , String fromMsisdn , String toMsisdn){
        JsonObject data = new JsonObject();
        data.addProperty("i", "1407393683");
        data.addProperty("bg_id", bg_id);
        
        JsonObject jsonCb = new JsonObject();
        jsonCb.addProperty("f", fromMsisdn);
        jsonCb.addProperty("to", toMsisdn);
        jsonCb.addProperty("t", "cbg");
        jsonCb.add("d", data);
        return jsonCb.toString();
	}

}

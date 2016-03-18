package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class BlockUnblockJson {
	
	/*
	 * creating json for blocking a contact
	 */
	public String blockContact(String msisdn){
        JsonObject jsonToBlock = new JsonObject();
        jsonToBlock.addProperty("t", "b");
        jsonToBlock.addProperty("d", msisdn); 
        return jsonToBlock.toString();
	}
	
	/*
	 * creating json for unblocking a contact
	 */
	public String unblockContact(String msisdn){
        JsonObject jsonToBlock = new JsonObject();
        jsonToBlock.addProperty("t", "ub");
        jsonToBlock.addProperty("d", msisdn); 
        return jsonToBlock.toString();
	}

}

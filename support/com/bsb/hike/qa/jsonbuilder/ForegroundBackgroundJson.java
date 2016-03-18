package com.bsb.hike.qa.jsonbuilder;

import com.google.gson.JsonObject;

public class ForegroundBackgroundJson {
	/*
	 * creating json packet when user comes in foreground
	 */
	public JsonObject fgPacket(){
        JsonObject payload = new JsonObject();
        JsonObject lastSeen = new JsonObject();
        lastSeen.addProperty("justOpened", true);
        payload.addProperty("t", "app");
        payload.addProperty("st", "fg");
        payload.add("d", lastSeen);
        payload.addProperty("ts", System.currentTimeMillis());
        return payload;
	}
	
	/*
	 * creating json packet when user goes in background
	 */
	public JsonObject bgPacket(){
        JsonObject payload = new JsonObject();
        payload.addProperty("t", "app");
        payload.addProperty("st", "bg");
        payload.addProperty("ts", System.currentTimeMillis());
        return payload;
	}
	
	/*
	 * creating json packet when user disables last seen setting
	 */
	public JsonObject disableLastSeen(){
        JsonObject payload = new JsonObject();
        JsonObject lastseen = new JsonObject();
        lastseen.addProperty("lastseen", false);
        payload.addProperty("t", "ac");
        payload.add("d", lastseen);
        return payload;
	}

}

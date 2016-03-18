package com.bsb.hike.qa.apisupport;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.core.HttpCore;
import com.bsb.hike.core.MqttCore;
import com.bsb.hike.qa.httpservice.HTTPServices.HttpPostResponse;
import com.bsb.hike.qa.jsonbuilder.GroupChatJson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;

public class GroupChatMessageSupport extends BaseClass{
	MqttCore mqttCore = new MqttCore();
	HttpCore httpCore = new HttpCore();
	DBObject db;
	JsonObject json;
	int Initial_Group_Length,Final_Group_Length;
	int gsize;
	HttpPostResponse response=null;
	JsonObject data;
	
	public static void main(String a[])
	{
		GroupChatMessageSupport gsu = new GroupChatMessageSupport();
		String msisdnGroupCreator = "+91151"+RandomStringUtils.randomNumeric(7);
		gsu.createHikeUser(msisdnGroupCreator);
		List<String> groupParticipants= new ArrayList<String>();
		groupParticipants.add("+91151"+RandomStringUtils.randomNumeric(7));
		groupParticipants.add("+91151"+RandomStringUtils.randomNumeric(7));
		gsu.createGroupAndSendMessage(msisdnGroupCreator, groupParticipants, "Test Group 1");
	}
	
	public void setPin(String groupId , String msisdn , String pinText){
		mqttCore.setPinInGroup(groupId, msisdn, pinText);
		}


	public void addMemberToGroup(String groupId, String msisdnAddingMember , String msisdnToAdd) throws InterruptedException{
		List<String> groupParticipants= new ArrayList<String>();
		groupParticipants.add(msisdnToAdd);
	    Initial_Group_Length = findGroupSize(groupId);
		mqttCore.addMembersToGroup(groupId, msisdnAddingMember, groupParticipants);
		Thread.sleep(2000);
	    Final_Group_Length = findGroupSize(groupId);
	    Assert.assertTrue("Error!! Member is not added to the group",Initial_Group_Length+1 == Final_Group_Length);
	}

	public void addMembersListToGroup(String groupId, String msisdnAddingMember , List <String> groupParticipants){
		try{
		gsize = groupParticipants.size();
	    Initial_Group_Length = findGroupSize(groupId);
		mqttCore.addMembersToGroup(groupId, msisdnAddingMember, groupParticipants);
		Thread.sleep(1000);
	    Final_Group_Length = findGroupSize(groupId);
	    Assert.assertTrue("Error!! Members are not added to the group", Final_Group_Length == Initial_Group_Length+gsize);
		}
		catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public void removeMemberFromGroup(String gid ,String msisdnCreator , String msisdnToRemove){
	    Initial_Group_Length = findGroupSize(gid);
		mqttCore.removeMemberFromGroup(gid, msisdnCreator, msisdnToRemove);
		Final_Group_Length = findGroupSize(gid);
		Assert.assertTrue("Error!! Member is not removed from the group", Final_Group_Length == Initial_Group_Length-1);
	}
	
	public void changeGroupNameByMember(String groupId ,String msisdn , String groupNewName){
		try {
            String postData = GroupChatJson.editGroupNameJson(groupNewName);
			httpCore.editGroupName(msisdn, postData, groupId);
			data = (JsonObject)new JsonParser().parse(response.responseStr);
			Assert.assertTrue("Error!! Response code is not 200", response.responseCode==200);  
			Assert.assertTrue("Error!! Response status is not ok", "ok".equalsIgnoreCase(data.get("stat").getAsString()));
			String groupNameUpdated = redis.hget("groupname", groupId);
			Assert.assertTrue("Error!! Group name in redis does not match with updated group name",groupNewName.equals(groupNameUpdated));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createGroupAndSendMessage(String msisdnGroupCreator , List<String> msisdnReceiverList , String groupName) {
		String groupId = null;
		int counter = 0;
		try
		{
			long timestamp = System.currentTimeMillis();        
		    groupId = getuidFromMsisdn(msisdnGroupCreator) + ":" + timestamp;
			mqttCore.connectAndPublishGCJ(groupName,groupId, msisdnGroupCreator, msisdnReceiverList); 
			DBObject group = mongo.getGroup(groupId);
			while(group == null && counter++ < 6)
			{
				Thread.sleep(1000);
				group = mongo.getGroup(groupId);
			}
			Assert.assertTrue("Group is not created at Server: "+group, group != null);
			mqttCore.connectAndPublishGCMessage(groupId, msisdnGroupCreator);
			return groupId;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return groupId;
	}


	public void groupCreatorLeavingGroup(String groupId , String msisdnCreator){
		try {
			Initial_Group_Length = findGroupSize(groupId);
			mqttCore.connectAndPublishGCL(groupId, msisdnCreator);
			Final_Group_Length = findGroupSize(groupId);
			Assert.assertTrue("Error!! Member is not removed from the group", Final_Group_Length == Initial_Group_Length-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeGroupProfile(String groupId , String msisdnChangingAvatar) throws Exception{
		try {
			HttpPostResponse response = httpCore.updateGroupAvatar(msisdnChangingAvatar, groupId);
			Assert.assertTrue("Error!! Response code is not 200", response.responseCode==200);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int findGroupSize(String groupId)
	{
		db = mongo.getGroup(groupId);
		if(db != null)
		{
		json = (JsonObject)new JsonParser().parse(db.toString());
		}
		return json.getAsJsonArray("users").size();
	}
	
	public void createHikeUser(String msisdn)
	{
		httpCore.createUser(msisdn);
	}

	
	public void sendHikeSticker(String msisdnSender , String groupId ) {
	      try
	      { 
	    	  mqttCore.connectAndSendHikeSticker(msisdnSender, groupId, "indian", "040_waah.png");
	    	
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	
	      }
	}
	

	public void sendHikeMessage(String msisdnSender , String groupId , String message) {
	      try
	      { 
	    	  mqttCore.connectAndSendHikeMessage(msisdnSender, groupId , message);
	    	
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	
	      }
	}
	
	public void sendHikeNudge(String msisdnSender , String groupId ) {
	      try
	      { 
	    	  mqttCore.connectAndSendHikeNudge(msisdnSender, groupId);
	    	
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	
	      }
	}
}




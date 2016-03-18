package com.bsb.hike.core;


import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.qa.constants.AutomationConstants;
import com.bsb.hike.qa.jsonbuilder.BlockUnblockJson;
import com.bsb.hike.qa.jsonbuilder.FriendJson;
import com.bsb.hike.qa.jsonbuilder.GroupChatJson;
import com.bsb.hike.qa.jsonbuilder.HikeMessageJson;
import com.bsb.hike.qa.jsonbuilder.StealthJson;
import com.bsb.hike.qa.jsonbuilder.startEndTypingJson;


public class MqttCore extends BaseClass{
	
	/*
	 * connect and block contact
	 */
	public MqttAsyncClient connectAndBlockContact(String blockerMsisdn , String msisdnToBlock){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(blockerMsisdn);
			Thread.sleep(AutomationConstants.MIN_WAIT_Time);
			BlockUnblockJson blockUnblockJson = new BlockUnblockJson();
			String blockPacket = blockUnblockJson.blockContact(msisdnToBlock);
        	publishMessage(blockPacket , getuidFromMsisdn(blockerMsisdn) , mqttAsyncClient);
        	return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * connect and Unblock Contact
	 */
	public MqttAsyncClient connectAndUnblockContact(String blockerMsisdn , String msisdnToUnBlock){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(blockerMsisdn);
			BlockUnblockJson blockUnblockJson = new BlockUnblockJson();
			String unblockPacket = blockUnblockJson.unblockContact(msisdnToUnBlock);
        	publishMessage(unblockPacket , getuidFromMsisdn(blockerMsisdn) , mqttAsyncClient);
        	return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * connect and send hike message
	 */
	public MqttAsyncClient connectAndSendHikeMessage(String msisdnSender , String msisdnReceiver , String message){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnSender);
			Thread.sleep(AutomationConstants.MIN_WAIT_Time);
			String messagePacket = HikeMessageJson.hikeMessage(msisdnReceiver,message);
            publishMessage(messagePacket , getuidFromMsisdn(msisdnSender) , mqttAsyncClient);
            Thread.sleep(AutomationConstants.MIN_WAIT_Time);
            return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error!! Message not sent");
		}
		return null;
	}
	
	/*
	 * Connect and Send sms message
	 */
	public MqttAsyncClient connectAndSendSmsMessage(String hikeUser, String smsUser){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(hikeUser);
			Thread.sleep(AutomationConstants.MIN_WAIT_Time);
			String messagePacket = HikeMessageJson.smsMessage(smsUser);
            publishMessage(messagePacket , getuidFromMsisdn(hikeUser) , mqttAsyncClient);
            Thread.sleep(AutomationConstants.MIN_WAIT_Time);
            return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Connect and send Invite
	 */
	public MqttAsyncClient connectAndSendInvite(String hikeUser , String smsUser){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(hikeUser);
			Thread.sleep(AutomationConstants.MIN_WAIT_Time);
			String messagePacket = HikeMessageJson.hikeSmsInvite(smsUser);
            publishMessage(messagePacket , getuidFromMsisdn(hikeUser) , mqttAsyncClient);
            Thread.sleep(AutomationConstants.MIN_WAIT_Time);
            return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public MqttAsyncClient connectAndSendHikeSticker(String msisdnSender , String msisdnReceiver , String categoryId, String stickerId){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnSender);
			Thread.sleep(AutomationConstants.MIN_WAIT_Time);
			String messagePacket = HikeMessageJson.hikeSticker(msisdnReceiver, categoryId, stickerId);
            publishMessage(messagePacket , getuidFromMsisdn(msisdnSender) , mqttAsyncClient);
            Thread.sleep(AutomationConstants.MIN_WAIT_Time);
            return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error!! Sticker not sent");
		}
		return null;
	}
	
	public MqttAsyncClient connectAndSendHikeNudge(String msisdnSender , String msisdnReceiver){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnSender);
			Thread.sleep(AutomationConstants.MIN_WAIT_Time);
			String messagePacket = HikeMessageJson.hikeNudge(msisdnReceiver);
            publishMessage(messagePacket , getuidFromMsisdn(msisdnSender) , mqttAsyncClient);
            Thread.sleep(AutomationConstants.MIN_WAIT_Time);
            return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error!! Nudge not sent");
		}
		return null;
	}
	
	
	/*
	 * Connect and Add friend
	 */
	public MqttAsyncClient connectAndAddFriend(String senderMsisdn , String msisdnToAdd ){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(senderMsisdn);
			FriendJson friendJson = new FriendJson();
			String jsonPacket = friendJson.addFavorite(msisdnToAdd);	
			publishMessage(jsonPacket , getuidFromMsisdn(senderMsisdn) , mqttAsyncClient);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Connect and Remove Friend
	 */
	public MqttAsyncClient connectAndRemoveFriend(String senderMsisdn , String msisdnToRemove){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(senderMsisdn);
			FriendJson friendJson = new FriendJson();
			String jsonPacket = friendJson.removeFavorite(msisdnToRemove);	
			publishMessage(jsonPacket , getuidFromMsisdn(senderMsisdn) , mqttAsyncClient);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * Connect and Enable Stealth
	 */
	public MqttAsyncClient connectAndEnableStealth(String msisdnEnablingStealth , String[] msisdnsTobeHided ){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnEnablingStealth);
			String enableStealthJson = StealthJson.enableStealthJson(msisdnsTobeHided);	
			publishMessage(enableStealthJson, getuidFromMsisdn(msisdnEnablingStealth), mqttAsyncClient);
			Thread.sleep(2000);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Connect and Enable Stealth
	 */
	public MqttAsyncClient connectAndDisableStealth(String msisdnDisablingStealth, String[] msisdnToDisableStealth ){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnDisablingStealth);
			String disableStealthJson = StealthJson.disableStealthJson(msisdnToDisableStealth);		
			publishMessage(disableStealthJson, getuidFromMsisdn(msisdnDisablingStealth), mqttAsyncClient);
			Thread.sleep(2000);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * connect and Enable Stealth in GC
	 */
	public MqttAsyncClient connectAndEnableStealthInGc(String msisdnEnablingStealth , String[] groupToEnableStealth ){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnEnablingStealth);
			String enableStealthJson = StealthJson.enableStealthJson(groupToEnableStealth);		
			publishMessage(enableStealthJson, getuidFromMsisdn(msisdnEnablingStealth), mqttAsyncClient);
			Thread.sleep(2000);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Connect and Disable Stealth on gc
	 */
	public MqttAsyncClient connectAndDisableStealthInGc(String msisdnDisablingStealth  ,String[] groupToDisableStealth){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnDisablingStealth);
			String disableStealthJson = StealthJson.disableStealthJson(groupToDisableStealth);	
			publishMessage(disableStealthJson, getuidFromMsisdn(msisdnDisablingStealth), mqttAsyncClient);
			Thread.sleep(2000);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MqttAsyncClient connectAndPublishGCJ(String groupName , String groupId , String msisdnCreator , List<String> groupParticipantsList){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnCreator);
			String gcjJson = GroupChatJson.gcjWithGroupName(groupName, groupId, msisdnCreator, groupParticipantsList);
			System.out.println("Group Json:"+gcjJson);
			publishMessage(gcjJson, getuidFromMsisdn(msisdnCreator) , mqttAsyncClient);
			Thread.sleep(2000);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MqttAsyncClient connectAndPublishGCL(String groupId , String msisdn){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdn);
			String gclJson = GroupChatJson.gclJson(groupId);
			publishMessage(gclJson, getuidFromMsisdn(msisdn) , mqttAsyncClient);
			Thread.sleep(2000);
			return mqttAsyncClient;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void connectAndPublishGCMessage(String groupId , String msisdn ){
		try {
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdn);
			String gcMessage = GroupChatJson.gcMessage(groupId);
			publishMessage(gcMessage, getuidFromMsisdn(msisdn) , mqttAsyncClient);
			mqttAsyncClient.disconnect();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Add multiple members to Group
	 */
	public void addMembersToGroup(String groupId , String msisdnAddingMembers , List <String> groupParticipants )
	{
		try
		{
		MqttAsyncClient	mqttClient=connectAndSubscribe(msisdnAddingMembers);
		String	gcjJson = GroupChatJson.addMembersJson(groupId,groupParticipants);
		publishMessage(gcjJson, getuidFromMsisdn(msisdnAddingMembers) ,mqttClient);
		mqttClient.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Remove member to group
	 */
	public void removeMemberFromGroup(String groupId,String msisdnRemovingMember , String msisdnToRemove)
	{
		try
		{
			MqttAsyncClient mqttAsyncClient = connectAndSubscribe(msisdnRemovingMember);
			String gckJson = GroupChatJson.gckJson(groupId , msisdnToRemove);
			publishMessage(gckJson, getuidFromMsisdn(msisdnRemovingMember) , mqttAsyncClient);
			Thread.sleep(2000);
			mqttAsyncClient.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Set Pin in group
	 */
	public void setPinInGroup(String groupId , String msisdn , String pinText){
		try {
			String pinJson = GroupChatJson.setPINJson(groupId , pinText);
			MqttAsyncClient mqttClientOwner = connectAndSubscribe(msisdn);
			publishMessage(pinJson,  getuidFromMsisdn(msisdn) , mqttClientOwner);
			mqttClientOwner.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * connect and Start Typing
	 */
	public MqttAsyncClient connectAndstartTyping(String msisdnSender, String msisdnReceiver){
		try { 
			MqttAsyncClient mqttClient = connectAndSubscribe(msisdnSender); 
			String messagePacket = startEndTypingJson.startTyping(msisdnSender, msisdnReceiver);
            publishMessage(messagePacket, getuidFromMsisdn(msisdnSender) , mqttClient);
            Thread.sleep(AutomationConstants.MIN_WAIT_Time);
            return mqttClient;
        } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}


package com.bsb.hike.qa.apisupport;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.core.MqttCore;

public class FriendsActionSupport extends BaseClass{

	public static void main(String a[])
	{
		FriendsActionSupport fas = new FriendsActionSupport();
		fas.setSmsCount("+917777774045", "50");
	}
    MqttCore mqttCore = new MqttCore();	
	  public void create2WayFriendship(String msisdn1 , String msisdn2) {
	      try
	      {   
		  System.out.println("creating friendship");
	    	  mqttCore.connectAndAddFriend(msisdn1, msisdn2);
	    	  mqttCore.connectAndAddFriend(msisdn2, msisdn1);
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	  Assert.fail();
	      }
	  }
	
	  public void sendFriendRequest(String msisdnFriendrequestSender , String msisdnFriendRequestReceiver) {
	      try
	      {   
	    	  mqttCore.connectAndAddFriend(msisdnFriendrequestSender, msisdnFriendRequestReceiver);
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	  Assert.fail();
	      }
	  }
	  
	  public void removeFriendShip(String msisdnSendingRemovePacket , String msisdnReceivingRemovePacket){
		  try {
			  mqttCore.connectAndRemoveFriend(msisdnSendingRemovePacket,msisdnReceivingRemovePacket);
	} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  public void setSmsCount(String msisdn , String smsCountToSet){
		  try {
			  String uid = getuidFromMsisdn(msisdn);
			  redis.hset("em_" + uid , "h2sjb", smsCountToSet);
			  String smsCount = redis.hget("em_" + uid , "h2sjb");
	         
          System.out.println(StringUtils.equals(smsCount, smsCountToSet));
          Assert.assertTrue("sms count is not set", StringUtils.equals(smsCount, smsCountToSet));
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  
	  public void clearCacheAfterDelete(String msisdn){
		  try {
			  redis.hdel("delsmscredits", msisdn);
//			  redis.hdel("recurringCredits", msisdn);
			  System.out.println("deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
  

}
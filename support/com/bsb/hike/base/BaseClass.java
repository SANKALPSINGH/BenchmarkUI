package com.bsb.hike.base;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import com.bsb.hike.mqtt.MqttHandler;
import com.bsb.hike.qa.apisupport.ConsoleDataSupport;
import com.bsb.hike.qa.dbmanager.MongoDBManagerUtil;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.mongodb.DB;

/*
 * All test classes need to extend this BaseClass which contains global attributes and
 * frequently used behavior
 */
public class BaseClass{
//	public static void main(String[] args){
//		BaseClass b = new BaseClass();
//		//System.out.println(b.getRemainingSMSCredits("+919810771083"));
//	}

	public final String JSONTYPE = "application/json";
	public String uri = HikeAutomationConfig.getInstance().getTcpuri();
    public MongoDBManagerUtil mongo;
    public RedisServiceManagerUtil redis;
    public DB userDB;
    public String httphost = HikeAutomationConfig.getInstance().getHttphost();
    public int httpport = HikeAutomationConfig.getInstance().getHttpport();


    public BaseClass()
    {
        HikeAutomationConfig.getInstance();
        mongoDBSetup();
        redisSetup();
    }


  /*
   * Initialising mongo db
   */
  public void mongoDBSetup(){
        try
        {
            mongo = MongoDBManagerUtil.getInstance();
            userDB = mongo.getMongo().getDB(HikeAutomationConfig.getInstance().getMongoUserDBName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
  }


  /*
   * Initialising redis
   */
  public void redisSetup(){
      try{
        redis = RedisServiceManagerUtil.getInstance();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
  }


  /*
   * Fetching uid from mongo
   */
  public String getuidFromMsisdn(String msisdn){
	     try
	   {
	         ConsoleDataSupport cs = new ConsoleDataSupport();
	         return(cs.getUidFromConsole(msisdn));
	   }
	   catch (Exception e)
	   {
	       e.printStackTrace();
	   }
	   return null;
	 }

  /*
   * fetching token from mongo
   */
  public String getTokenFromMsisdn(String msisdn){
	     try
	   {
	         ConsoleDataSupport cs = new ConsoleDataSupport();
	         return(cs.getTokenFromConsole(msisdn));
	   }
	   catch (Exception e)
	   {
	       e.printStackTrace();
	   }
	   return null;
	 }


  public String getBackUpTokenHere(String msg){
		System.out.println("Getting backup key for :"+ msg);
		return mongo.getBackUpToken(msg);
		}

  /*
   * Logic for getting current SMS left with hike user
   */
  public int getRemainingSMSCredits(String msisdn) {
      try{
      String uid = getuidFromMsisdn(msisdn);
      Map<String , String> list = redis.hgetAll("em_"+uid);
      double transactionScore = 0.0;

      int sentMessages = (list.get("sm") !=null) ?  Integer.parseInt(list.get("sm")) : 0;

      int receivedMessages = (list.get("rm") !=null) ?  Integer.parseInt(list.get("rm")) : 0;



      int gcSentMessages = (list.get("gcsm") !=null) ?  Integer.parseInt(list.get("gcsm")) : 0;

      int gcReceivedMessages = (list.get("gcrm") !=null) ?  Integer.parseInt(list.get("gcrm")) : 0;



      transactionScore = (1 * Math.min(sentMessages, receivedMessages)) +

      (0.2 * Math.min(gcSentMessages, gcReceivedMessages));



      int h2sEmLinked = (int) Math.floor((2*(Math.pow(transactionScore, 0.55))));

      int h2sJoiningBonus = (list.get("h2sjb") !=null) ?  Integer.parseInt(list.get("h2sjb")) : 0;

      int h2sEarned = (list.get("h2se") !=null) ?  Integer.parseInt(list.get("h2se")) : 0;

      int h2sManualBuffer = (list.get("h2smb") !=null) ?  Integer.parseInt(list.get("h2smb")) : 0;

      int h2sCoupons = (list.get("h2sc") !=null) ?  Integer.parseInt(list.get("h2sc")) : 0;

      int h2sSent = (list.get("h2ss") !=null) ?  Integer.parseInt(list.get("h2ss")) : 0;



      int currentH2S = (h2sJoiningBonus + h2sEarned + h2sManualBuffer + h2sEmLinked + h2sCoupons) - h2sSent;

      return (currentH2S >= 0) ? currentH2S : 0;

      }

      catch(Exception e){

      e.printStackTrace();

      }

      return 0;

        }


  /*
   * Connecting and subscribing to all channels
   */
  public MqttAsyncClient connectAndSubscribe(String clientId) throws MqttException, InterruptedException
  {
  	MqttHandler mqttConnect = new MqttHandler();
  	return mqttConnect.connectAndSubscribe(uri, clientId , getuidFromMsisdn(clientId), getTokenFromMsisdn(clientId));
  }

  /*
   * Publishing mqtt message
   */
  public void publishMessage(String msg , String uid ,MqttAsyncClient mqttAsyncClientSender) throws MqttException, InterruptedException
  {
      mqttAsyncClientSender.publish(uid+"/p",msg.getBytes(),1,false);
      Thread.sleep(100);

  }

  /*
   * Disconnecting mqtt channel
   */
  public void disconnectChannel(MqttAsyncClient mqttAsyncClient){
  	try {
  			if(mqttAsyncClient.isConnected())
  			{
  				mqttAsyncClient.disconnect();
  			}

		} catch (Exception e) {
			e.printStackTrace();
		}
  }

}



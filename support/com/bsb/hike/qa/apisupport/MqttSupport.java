package com.bsb.hike.qa.apisupport;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.core.MqttCore;
import com.bsb.hike.mqtt.MqttHandler;
import com.bsb.hike.qa.jsonbuilder.ForegroundBackgroundJson;
import com.google.gson.JsonObject;

public class MqttSupport extends BaseClass{


    MqttCore mqttCore = new MqttCore();
    MqttAsyncClient mqttClient =null;
    JsonObject payload;
    ForegroundBackgroundJson fgbgJson = new ForegroundBackgroundJson();
    public static void main(String[] args){
	MqttSupport t = new MqttSupport();
	t.sendTypingNotification("+912222222200", "+9199993333");
	//		t.getUserOnline("+916666664045");
	//		t.generateReadMessagePacket("+9199993333","+916666664045");
	try{
	    Thread.sleep(10000);
	}
	catch(Exception e){
	    e.printStackTrace();
	}
	//		t.closeConnection();
    }

    public MqttAsyncClient getUserOnline(String msisdn)
    {
	MqttAsyncClient mqtt = null;
	try {
	    mqtt = connectAndSubscribe(msisdn);
	    payload = fgbgJson.fgPacket();
	    publishMessage(payload.toString() , getuidFromMsisdn(msisdn) , mqtt);
	} catch (Exception e) {
	    e.printStackTrace();
	    Assert.fail("Error!! Unable to bring user online");
	}
	return mqtt;
    }

    public void getUserOffline(MqttAsyncClient mqtt)
    {
	disconnectChannel(mqtt);
    }
    public void sendTypingNotification(String msisdnSender , String msisdnReceiver){
	try {
	    MqttAsyncClient mqttAsyncClient = mqttCore.connectAndstartTyping(msisdnSender, msisdnReceiver);
	    Thread.sleep(20000);
	    disconnectChannel(mqttAsyncClient);
	} catch (Exception e) {
	    e.printStackTrace();
	    Assert.fail("Error!! Typing notification is not sent");
	}
    }

    public boolean isStartTypingNotifReceived(String msisdnTypingText){
	boolean isSTReceived = false;
	try {
	    String grepTextST = "\"f\":\"" + msisdnTypingText + "\",\"t\":\"st\"";
	    isSTReceived = grepTextInMqttChannel(grepTextST);
	    //"\"f\":\"+917777775841\",\"t\":\"st\"";

	    return isSTReceived;
	} catch (Exception e) {
	    e.printStackTrace();
	    Assert.fail("Error!! start typing notification not received");
	}
	return isSTReceived;
    }

    public boolean isEndTypingNotifReceived(String msisdnTypingText){
	boolean isETReceived = false;
	try {
	    String grepTextET = "\"f\":\"" + msisdnTypingText + "\",\"t\":\"et\"";
	    isETReceived = grepTextInMqttChannel(grepTextET);
	    //"\"f\":\"+917777775841\",\"t\":\"st\"";

	    return isETReceived;
	} catch (Exception e) {
	    e.printStackTrace();
	    Assert.fail("Error!! end typing notification not received");
	}
	return isETReceived;
    }

    public void deliverMessage(String msisdn){
	try {
	    getUserOnline(msisdn);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }


    public boolean grepTextInMqttChannel(String text){
	try {
	    boolean isTextPresent = false;
	    int waitTime = 0;
	    while(waitTime<20){
		List<String> messageList = MqttHandler.arrivedMessages;
		for(String msg:messageList){
		    if(msg.contains(text)){
			isTextPresent = true;
			System.out.println("wait time before success(sec): " + waitTime);
			return isTextPresent;
		    }
		}
		if(!isTextPresent){
		    waitTime++;
		    Thread.sleep(1000);
		}

	    }
	    System.out.println("wait time before failure(sec): " + waitTime);
	    return isTextPresent;
	} catch (Exception e) {
	    e.printStackTrace();
	    Assert.fail("Error!! Text not arrived in Mqtt channel");
	}
	return false;
    }
}

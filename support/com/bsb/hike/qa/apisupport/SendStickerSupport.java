package com.bsb.hike.qa.apisupport;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.core.MqttCore;

public class SendStickerSupport extends BaseClass{

	MqttCore mqttCore = new MqttCore();
	public void sendSticker(String msisdnSender , String msisdnReceiver , String categoryId, String stickerId) {
		try
		{  
			mqttCore.connectAndSendHikeSticker(msisdnSender, msisdnReceiver, categoryId, stickerId);
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	public void sendNudge(String msisdnSender , String msisdnReceiver) {
		try
		{  
			
			mqttCore.connectAndSendHikeNudge(msisdnSender, msisdnReceiver);
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}
}
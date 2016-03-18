package com.bsb.hike.mqtt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHandler {
    // private MqttAsyncClient m = null;
    public static List<String> arrivedMessages = new ArrayList<String>();

    /*
     * connecting a hike user to mqtt channel and subscribing to all channels
     */
    public MqttAsyncClient connectAndSubscribe(String uri, String clientId,
	    String uid, String token) {
	MqttAsyncClient m = null;
	try {

	    m = new MqttAsyncClient(uri, clientId, null);

	    MqttConnectOptions op = new MqttConnectOptions();
	    op.setCleanSession(false);
	    op.setUserName(uid);
	    op.setPassword(token.toCharArray());
	    op.setConnectionTimeout(0);

	    @SuppressWarnings("unused")
	    IMqttToken t = m.connect(op, null, null);

	    Thread.sleep(2000);
	    String[] topics = new String[3];
	    topics[0] = uid + "/s";
	    topics[1] = uid + "/a";
	    topics[2] = uid + "/u";
	    int[] qos = new int[] { 0, 1, 2 };
	    m.subscribe(topics, qos);

	    m.setCallback(new MqttCallback() {
		// overriding this method so that we can capture the messages
		// received by user connected on mqtt channels
		public void messageArrived(String arg0, MqttMessage arg1)
			throws Exception {

		    arrivedMessages.add(new String(arg1.getPayload()));

		}

		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}

		public void connectionLost(Throwable arg0) {

		}

	    });
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return m;
    }

    /*
     * This method is used for asserting whether desired packet has arrived on
     * the channel on which client is connected
     */

    public static boolean payloadArrived(String expectedText) {
	int sleepTimer = 0;
	while (sleepTimer < 50) {
	    for (String msg : arrivedMessages) {
		if (msg.contains(expectedText)) {

		    return true;
		}
	    }
	    sleepTimer++;
	}
	return false;
    }

    /*
     * This method is used to check whether sms count packet has arrived on the
     * channel on which client is connected
     */
    public static String getSCPacket() {
	for (String msg : arrivedMessages) {
	    if (msg.contains("\"t\":\"sc\"")) {

		return msg;
	    }
	}
	return null;
    }

    public static String getHmPacket() {
	for (String msg : arrivedMessages) {
	    if (msg.contains("\"t\":\"m\"")) {
		return msg;
	    }
	}
	return null;
    }
}

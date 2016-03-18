package com.bsb.hike.mqtt.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;

import com.bsb.hike.mqtt.msg.ConnAckMessage;
import com.bsb.hike.mqtt.msg.ConnectMessage;
import com.bsb.hike.mqtt.msg.DisconnectMessage;
import com.bsb.hike.mqtt.msg.Message;
import com.bsb.hike.mqtt.msg.MessageInputStream;
import com.bsb.hike.mqtt.msg.MessageOutputStream;
import com.bsb.hike.mqtt.msg.PingReqMessage;
import com.bsb.hike.mqtt.msg.PingRespMessage;
import com.bsb.hike.mqtt.msg.PubAckMessage;
import com.bsb.hike.mqtt.msg.PublishMessage;
import com.bsb.hike.mqtt.msg.QoS;
import com.bsb.hike.mqtt.msg.RetryableMessage;
import com.bsb.hike.mqtt.msg.SubAckMessage;
import com.bsb.hike.mqtt.msg.SubscribeMessage;
import com.bsb.hike.mqtt.msg.UnsubscribeMessage;

public class MqttConnection
{
	short nextMessageId = 1;
    public short getNextMessageId() {
        short rc = nextMessageId;
        nextMessageId++;
        if(nextMessageId==0) {
            nextMessageId=1;
        }
        return rc;
    }

	private MessageInputStream in;
	private Socket socket;
	private MessageOutputStream out;
	private MqttReader reader;
	//protected final Semaphore connectionAckLock= new Semaphore(0);
	protected final SynchronousQueue<ConnAckMessage> connectionAck=new SynchronousQueue<ConnAckMessage>();
	private final String id;
	private volatile boolean stopped;
	private volatile boolean connected;
	public MqttConnection(String id) {
		this.id = id;
		this.stopped=true;
		this.connected=false;
    }

    public void connect(String host, int port)
			throws UnknownHostException, IOException, InterruptedException {
		connect(host, port, null,null);
	}
	public ConnAckMessage connect(String host, int port,String username,String password)
			throws UnknownHostException, IOException, InterruptedException {
		/* Following code can connect to host with specified connection timeout (SO_TIMEOUT)
		SocketAddress sockaddr = new InetSocketAddress(host, port);
		socket = new Socket();
		socket.connect(sockaddr, 15*1000);*/
		if(stopped==false)
			throw new IOException("Already in connecting state");
		stopped=false;
		socket = new Socket(host, port);
		InputStream is = socket.getInputStream();
		in = new MessageInputStream(is);
		OutputStream os = socket.getOutputStream();
		out = new MessageOutputStream(os);
		reader = new MqttReader();
		reader.start();
		ConnectMessage msg = new ConnectMessage(id, false, (byte)60);
		if(username!=null)
			msg.setCredentials(username, password);
		out.writeMessage(msg);
		return  connectionAck.take();
	}

	public boolean ping() throws IOException{
		PingReqMessage msg=new  PingReqMessage();
		out.writeMessage(msg);
		return true;
	}
	public void publish(String topic,byte[] message,QoS qos) throws IOException {
		PublishMessage msg = new PublishMessage(topic,message,qos);
		msg.setMessageId(getNextMessageId());
		out.writeMessage(msg);
	}
	public void subscribe(String topic) throws IOException {
		SubscribeMessage msg = new SubscribeMessage(topic, QoS.AT_MOST_ONCE);
		out.writeMessage(msg);
	}
	public void subscribe(String topic,QoS qos) throws IOException {
		SubscribeMessage msg = new SubscribeMessage(topic, qos);
		out.writeMessage(msg);
	}
	public void unsubscribe(UTF8Buffer topic) throws IOException {
		unsubscribe(topic.getString());
	}
	public void unsubscribe(String topic) throws IOException {
		UnsubscribeMessage msg = new UnsubscribeMessage(topic);
		out.writeMessage(msg);
	}
	public void disconnect() throws IOException {
		stopped=true;
		if(connected){
			DisconnectMessage msg = new DisconnectMessage();
			if(out!=null) {
				out.writeMessage(msg);
				connected = false;
			}
		}
		if(socket!=null)
			socket.close();
	}
	public void writeMessage(Message message) throws IOException{
		out.writeMessage(message);
	}

	protected void submitMessage(Message message) throws IOException{
		writeMessage(message);
	}
	protected void handleFailure(Throwable t){
		System.out.println("Socket Failure From Server");
	}
	private void handleMessage(Message msg) {
		if (msg == null) {
			return;
		}
		switch (msg.getType()) {
		case CONNACK:
			handleMessage((ConnAckMessage) msg);
			break;
		case PUBLISH:
			handleMessage((PublishMessage) msg,new Acknowlegement((PublishMessage)msg));
			break;
		case PINGRESP:
			handleMessage((PingRespMessage)msg);
			break;
		case PUBACK:
			handleMessage((PubAckMessage)msg);
			break;
		case SUBACK:
			handleMessage((SubAckMessage)msg);
			break;
		default:
			break;
		}
	}

	protected void handleMessage(ConnAckMessage msg) {
		try
		{
			connected=true;
			connectionAck.put(msg);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	protected void handleMessage(PublishMessage msg,Runnable ack) {
		ack.run();
	}

    protected void sendAcknowledement(RetryableMessage msg){
		short messageId=msg.getMessageId();
		if(msg.getQos()==QoS.AT_LEAST_ONCE || msg.getQos()==QoS.EXACTLY_ONCE){
			try
			{
				submitMessage(new PubAckMessage(messageId));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public class Acknowlegement implements Runnable{

		RetryableMessage msg;
		public Acknowlegement(RetryableMessage msg)
		{
			this.msg=msg;
		}
		public void run()
		{
			sendAcknowledement(msg);
		}

	}

	protected void handleMessage(PingRespMessage msg) {
		System.out.println("PING response came at "+new Date());
	}
	protected void handleMessage(PubAckMessage msg) {
		System.out.println("PubAck came for msgId="+msg.getMessageId());
	}
	protected void handleMessage(SubAckMessage msg) {
		System.out.println("SubAck came for msgId="+msg.getMessageId()+" "+msg.getGrantedQoSs().toString());
	}

	private class MqttReader extends Thread {
		public MqttReader()
		{
			super("Mqtt-Reader-Thread");
		}

		@Override
		public void run() {

			Message msg;
			boolean exceptionPrinted=false;
			try {
				while (!stopped) {
					try{
						msg = in.readMessage();
						handleMessage(msg);
						exceptionPrinted=false;
					}
					catch(UnsupportedOperationException e){
						if(exceptionPrinted==false){
							e.printStackTrace();
							exceptionPrinted=true;
						}
					}

				}
			}
			catch(EOFException e){
				if(!stopped)
					handleFailure(e);
			}
			catch(SocketException e){
				if(!stopped)
					handleFailure(e);
			}
			catch (IOException e) {

				e.printStackTrace();
				if(!stopped)
					handleFailure(e);
			}
			finally{
			}
		}
	}

    public boolean isStopped()
    {
        return stopped;
    }
    public boolean isConnected()
    {
        return connected;
    }

}

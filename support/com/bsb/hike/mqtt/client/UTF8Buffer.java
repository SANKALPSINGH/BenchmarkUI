package com.bsb.hike.mqtt.client;

public class UTF8Buffer
{
	private String string;

	public UTF8Buffer(String string)
	{
		super();
		this.string = string;
	}

	public String getString()
	{
		return string;
	}

	public void setString(String string)
	{
		this.string = string;
	}
	
}

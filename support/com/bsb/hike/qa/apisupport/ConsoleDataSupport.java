package com.bsb.hike.qa.apisupport;

import com.bsb.hike.http.HTTPService;
import com.bsb.hike.http.HTTPService.HttpPostResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author amita
 *
 */
public class ConsoleDataSupport 
{
	 HTTPService httpService = new HTTPService();
	 
	 public String getPinFromConsole(String msisdn)
	 {
		 	String url = "http://stagingconsole.hike.in/api/v1/getpin/"+msisdn;
			HttpPostResponse response = HTTPService.getDataFromConsole(url);
			System.out.println("Response from console:"+response.responseStr);
			JsonObject data = (JsonObject)new JsonParser().parse(response.responseStr);
			System.out.println("Pin is "+data.get("pin").getAsString());
			return data.get("pin").getAsString();
	 }
	 
	 public String getUidFromConsole(String msisdn)
	 {
		 	String url = "http://stagingconsole.hike.in/api/v1/getuidtoken/"+msisdn;
		 	HttpPostResponse response = HTTPService.getDataFromConsole(url);
			System.out.println("Response from console:"+response.responseStr);		
			JsonObject data = (JsonObject)new JsonParser().parse(response.responseStr);
			return data.get("uid").getAsString();
	 }
	 
	 public String getTokenFromConsole(String msisdn)
	 {
		 	String url = "http://stagingconsole.hike.in/api/v1/getuidtoken/"+msisdn;
		 	HttpPostResponse response = HTTPService.getDataFromConsole(url);
			System.out.println("Response from console:"+response.responseStr);		
			JsonObject data = (JsonObject)new JsonParser().parse(response.responseStr);
			return data.get("token").getAsString();
	 }
	 
	public void deleteUserCompletelyFromServer(String msisdn) 
	{
		String url = "http://stagingconsole.hike.in/api/v1/accountall/" + msisdn.substring(1, msisdn.length());
		try 
		{
			HttpPostResponse response = HTTPService.deleteAccountCompletely(url, "application/json");
			System.out.println(response.responseStr);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	 public static void main(String a[])
	 {
		 ConsoleDataSupport c = new ConsoleDataSupport();
		 c.getPinFromConsole("+914444441206");
		 c.deleteUserCompletelyFromServer("+913658877210");
	 }
}

package com.bsb.hike.qa.apisupport;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.core.HttpCore;
import com.bsb.hike.qa.httpservice.HTTPServices.HttpPostResponse;
import com.bsb.hike.qa.jsonbuilder.StatusUpdateJson;

public class StatusUpdateSupport extends BaseClass{
		HttpCore httpCore = new HttpCore();
		HttpPostResponse response;
		JSONObject respJson, dataJson;
		String data, statusId;
		
		public static void main(String[] args){
		StatusUpdateSupport sus = new StatusUpdateSupport();
		sus.setStatusUpdate("+911510814300");
		}
		
		public void setStatusUpdate(String msisdnUserChangingStatus , String statusUpdateMessage , int mood) {
	      try
	      { 
	    	  String suJson = (new StatusUpdateJson()).statusUpdate(statusUpdateMessage, mood);
	    	  response = httpCore.setStatusUpdate(msisdnUserChangingStatus, suJson);
	    	  dataJson = (JSONObject) new JSONParser().parse(new String(response.responseStr));
	    	  Assert.assertTrue("Error!! Response Code != 200",response.responseCode==200);
	    	  data = dataJson.get("data").toString();
	    	  dataJson = (JSONObject) new JSONParser().parse(new String(data));
	    	  statusId = dataJson.get("statusid").toString();
	    	  Assert.assertTrue("Error!! status id is empty",!StringUtils.isEmpty(statusId));
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	 
	      }
	  }

	  public void setStatusUpdate(String msisdnUserChangingStatus) {
	      try
	      { 
	    	  String suJson = (new StatusUpdateJson()).statusUpdate();
	    	  response = httpCore.setStatusUpdate(msisdnUserChangingStatus, suJson);
	    	  dataJson = (JSONObject) new JSONParser().parse(new String(response.responseStr));
	    	  Assert.assertTrue("Error!! Response Code != 200",response.responseCode==200);
	    	  data = dataJson.get("data").toString();
	    	  dataJson = (JSONObject) new JSONParser().parse(new String(data));
	    	  statusId = dataJson.get("statusid").toString();
	    	  Assert.assertTrue("Error!! status id is empty",!StringUtils.isEmpty(statusId));
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	    	 
	      }
	  }

}
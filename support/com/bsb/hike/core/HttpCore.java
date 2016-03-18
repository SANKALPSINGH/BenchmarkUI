package com.bsb.hike.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.qa.constants.AutomationConstants;
import com.bsb.hike.qa.httpservice.HTTPServices;
import com.bsb.hike.qa.httpservice.HTTPServices.HttpPostResponse;
import com.bsb.hike.util.UserProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HttpCore extends BaseClass{

	/*
	 * Create New user returning Object of UserProperty
	 */
	public UserProperty createUser(String msisdn){
		String uid="",token="";
		UserProperty user=null;
		try {
			String accountUrl = "http://" + httphost + ":"+ httpport + AutomationConstants.CreateAccountUrl;
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("X-MSISDN", msisdn);
			JsonObject postData = registerDevice(msisdn);
			HttpPostResponse resp = HTTPServices.postDataToUrl(accountUrl, "application/json", headers, postData.toString());
			Thread.sleep(200);
			System.out.println(resp.responseStr);
			JSONObject respJson = (JSONObject) new JSONParser().parse(new String(resp.responseStr));
			uid = respJson.get("uid").toString();	
			token = respJson.get("token").toString();
			user=new UserProperty(msisdn,uid,token);
			updateName(user,"{\"name\" :\""+"Name"+RandomStringUtils.randomAlphabetic(3)+"\"}");
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public JsonObject registerDevice(String msisdn) throws Exception{
		registerPinRequest(msisdn);
		//Thread.sleep(AutomationConstants.MAX_WAIT_Time);
		String pinCode = redis.getUserPin(msisdn);
		Assert.assertTrue("PinCode retrieved from redis is Empty", !StringUtils.isEmpty(pinCode));
		JsonObject postData = new JsonObject();
		if(StringUtils.isNotBlank(pinCode)) {
			postData.addProperty("pin", pinCode);
			postData.addProperty("msisdn", msisdn);
		}
		postData.addProperty("dev_id", "27D017E4-DE87-443E-B207-ADC4670" + RandomStringUtils.randomAlphabetic(5).toUpperCase());
		postData.addProperty("dev_type", "iPhone");
		postData.addProperty("dev_version", "iPhone");
		postData.addProperty("appversion", "2.8.6.9");
		postData.addProperty("os_version", "7.1.2");
		postData.addProperty("os", "iPhone OS");
		postData.addProperty("device_key", "NA" );
		postData.addProperty("ddev_token", "F467E42860038340ED11A272CD028DB127BD82312968A4BF18FC81E8E0" + RandomStringUtils.randomAlphabetic(5).toUpperCase());
		return postData;
	}

	public HttpPostResponse registerPinRequest(String msisdn) throws Exception {
		String accountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.ValidatePin;
		Map<String,String> headers = new HashMap<String,String>();
		JsonObject postData = new JsonObject();
		postData.addProperty("phone_no", msisdn);
		return HTTPServices.postDataToUrl(accountUrl, JSONTYPE, headers, postData.toString());
	}

	public HttpPostResponse uploadAddressBook(List<String> msisdns , UserProperty user){
		try {
			JsonObject dataToPost = new JsonObject();
			for (String msisdn : msisdns) {
				String id = RandomStringUtils.randomNumeric(3);
				JsonArray jsonIndividualEntry = new JsonArray();
				JsonObject nameAndMsisdn = new JsonObject();
				nameAndMsisdn.addProperty("name", "Hike#"+RandomStringUtils.randomNumeric(4));
				nameAndMsisdn.addProperty("phone_no", msisdn);
				jsonIndividualEntry.add(nameAndMsisdn);
				dataToPost.add(id, jsonIndividualEntry);
			} 
			String accountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.UploadAddressBookUrl; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ user.getToken() + ";UID=" + user.getUid());
			return HTTPServices.postDataToUrl(accountUrl, JSONTYPE, headers, dataToPost.toString()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpPostResponse uploadAddressBook(String dataToPost , UserProperty userData){
		try {
			String accountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.UploadAddressBookUrl; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ userData.getToken()+ ";UID=" + userData.getUid());
			return HTTPServices.postDataToUrl(accountUrl, JSONTYPE, headers, dataToPost.toString()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/*
	 * Addressbook Upload 
	 */
	public HttpPostResponse uploadAddressBook(List<String> msisdns , List<String> names , UserProperty user){
		try {
			JsonObject dataToPost = new JsonObject();
			for (int i=0 ; i<msisdns.size() ; i++) {
				String id = RandomStringUtils.randomNumeric(3);
				JsonArray jsonIndividualEntry = new JsonArray();
				JsonObject nameAndMsisdn = new JsonObject();
				nameAndMsisdn.addProperty("name", names.get(i));
				nameAndMsisdn.addProperty("phone_no", msisdns.get(i));
				jsonIndividualEntry.add(nameAndMsisdn);
				dataToPost.add(id, jsonIndividualEntry);
			} 
			String accountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.UploadAddressBookUrl; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ user.getToken() + ";UID=" + user.getUid());
			return HTTPServices.postDataToUrl(accountUrl, JSONTYPE, headers, dataToPost.toString()); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/*
	 * Get Address Book From Hbase
	 */
	public JSONObject getAddressBook(UserProperty user){
		try {
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ user.getToken() + ";UID=" + user.getUid());
			String hBaseUrl = AutomationConstants.AB_RABUrl;
			HttpPostResponse hBaseResponse = HTTPServices.postDataToUrl(hBaseUrl, JSONTYPE, headers, user.getMsisdn());
			JSONObject dataJson = (JSONObject) new JSONParser().parse(new String(hBaseResponse.responseStr));
			return dataJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Upload User Avatar
	 */
	public HttpPostResponse uploadUserAvatar(UserProperty user, String filePath){
		try {
			String updateProfileUrl = "http://" + httphost + ":" + httpport + AutomationConstants.UpdateProfilePicUrl; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+user.getToken() + ";UID=" + user.getUid());
			return HTTPServices.postFileToURL(updateProfileUrl, "", headers, filePath); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Edit User Profile
	 */
	public HttpPostResponse editUserProfile(UserProperty user , String postData){
		try {
			String updateProfileUrl = "http://" + httphost + ":" + httpport + AutomationConstants.UpdateProfileUrl; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+user.getToken() + ";UID=" + user.getUid());     
			return HTTPServices.postDataToUrl(updateProfileUrl, JSONTYPE, headers, postData.toString());  

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Edit Group Name
	 */
	public HttpPostResponse editGroupName(String msisdn , String postData , String groupId){
		try {
			String changeGroupNameUrl = "http://"+httphost+":"+httpport+"/v1/group/"+groupId + "/name"; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ getTokenFromMsisdn(msisdn) + ";UID=" + getuidFromMsisdn(msisdn));
			return HTTPServices.postDataToUrl(changeGroupNameUrl, JSONTYPE, headers, postData.toString());       
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Set Status Update
	 */
	public HttpPostResponse setStatusUpdate(String msisdn , String suJson){
		try {
			String statusUpdateUrl = "http://"+httphost+":"+httpport+AutomationConstants.StatusUpdateUrl; 
			System.out.println("Json status update::"+suJson);
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+getTokenFromMsisdn(msisdn) + "; UID=" + getuidFromMsisdn(msisdn));
			return HTTPServices.postDataToUrl(statusUpdateUrl, JSONTYPE, headers, suJson); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Unlink User Account
	 */
	public HttpPostResponse unlinkUserAccount(UserProperty user){
		try {
			String unlinkAccountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.UnlinkAccountUrl;
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+user.getToken() + "; UID=" + user.getUid());
			return HTTPServices.postDataToUrl(unlinkAccountUrl, null, headers, "");    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpPostResponse deleteAccount(String msisdn){
		try {
			String accountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.DeleteAccountUrl;
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+getTokenFromMsisdn(msisdn) + "; UID=" + getuidFromMsisdn(msisdn));
			return HTTPServices.getData(accountUrl, JSONTYPE, headers, null,"DELETE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * Delete User Account
	 */
	public HttpPostResponse deleteUserAccount(UserProperty user){
		try {
			String accountUrl = "http://" + httphost + ":" + httpport + AutomationConstants.DeleteAccountUrl;
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+user.getToken() + "; UID=" + user.getUid());
			return HTTPServices.getData(accountUrl, JSONTYPE, headers, null,"DELETE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpPostResponse deleteAcountCompletely(String clientId){
		try {
			String deleteHikeAccountCompletelyUrl = AutomationConstants.DeleteHikeAccountCompletely + clientId.substring(1, clientId.length());
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user=" + getTokenFromMsisdn(clientId) + ";UID=" + getuidFromMsisdn(clientId));
			return HTTPServices.deleteAccountCompletely(deleteHikeAccountCompletelyUrl, JSONTYPE, headers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Delete Status Update
	 */

	public HttpPostResponse deleteStatus(UserProperty user , String statusId){
		try {
			String statusUpdateUrl = "http://"+httphost+":"+httpport+AutomationConstants.StatusUpdateUrl + "/" + statusId; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+user.getToken() + "; UID=" + user.getUid());
			return HTTPServices.getData(statusUpdateUrl, JSONTYPE, headers, null,"DELETE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpPostResponse getAccountProfile(UserProperty userData){
		try {
			String getAccountUrl = "http://"+httphost+":"+httpport+AutomationConstants.UpdateProfileUrl+"/%2B"+userData.getMsisdn().substring(1);
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+userData.getToken() + "; UID=" + userData.getUid());
			return HTTPServices.getData(getAccountUrl, "application/json", headers, "", "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpPostResponse getLastSeen(UserProperty userData , String msisdn){
		try {
			String lastSeenUrl = "http://" + httphost + ":" + httpport + AutomationConstants.GetLastSeen + "/" + msisdn ; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+userData.getToken() + "; UID=" + userData.getUid());
			return HTTPServices.getData(lastSeenUrl, JSONTYPE, headers, "", "GET"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/*
	 * Get Group Avatar
	 */
	public HttpPostResponse getGroupAvtar(UserProperty user, String groupID){
		try {
			String getGroupAvatarUrl = "http://" + httphost + ":" + httpport + "/v1/group/" + groupID + "/avatar"; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ user.getToken() + "; UID=" + user.getUid());
			return HTTPServices.getData(getGroupAvatarUrl, JSONTYPE, headers, null, "GET");         
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpPostResponse updateGroupAvatar(String msisdn, String groupID){
		try {
			String changeGroupAvatarUrl = "http://" + httphost + ":" + httpport + "/v1/group/" + groupID + "/avatar"; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ getTokenFromMsisdn(msisdn) + "; UID=" + getuidFromMsisdn(msisdn));
			String workingDir = AutomationConstants.WorkingDir;
			return HTTPServices.postFileToURL(changeGroupAvatarUrl, "", headers, workingDir+"/res/images.jpeg");           
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Upload Group Avatar
	 */
	public HttpPostResponse uploadGroupAvatar(String msisdn, String groupID , String filePath){
		try {
			String changeGroupAvatarUrl = "http://" + httphost + ":" + httpport + "/v1/group/" + groupID + "/avatar"; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+ getTokenFromMsisdn(msisdn) + "; UID=" + getuidFromMsisdn(msisdn));
			String workingDir = AutomationConstants.WorkingDir;
			return HTTPServices.postFileToURL(changeGroupAvatarUrl, "", headers, workingDir+filePath);            

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Update Name
	 */
	public HttpPostResponse updateName(UserProperty user,String postData)
	{
		try {
			String updateNameUrl = "http://" + httphost + ":" + httpport + AutomationConstants.SetAccountNameUrl; 
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Cookie", "user="+user.getToken() + ";UID=" + user.getUid());     
			return HTTPServices.postDataToUrl(updateNameUrl, JSONTYPE, headers, postData.toString());  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

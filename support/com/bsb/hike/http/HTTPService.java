package com.bsb.hike.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.bsb.hike.qa.constants.AutomationConstants;
import com.bsb.hike.qa.dbmanager.MongoDBManagerUtil;
import com.bsb.hike.qa.dbmanager.RedisServiceManagerUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DB;

public class HTTPService {
	String httphost = "staging.im.hike.in";
	int httpport = 8080;
	MongoDBManagerUtil mongo = MongoDBManagerUtil.getInstance();
    DB userDB = mongo.getMongo().getDB("userdb");
    RedisServiceManagerUtil redis = RedisServiceManagerUtil.getInstance();

    public void registerPinRequest(String host , int port , String msisdn) throws Exception {
        String accountUrl = "http://"+host+":"+port+AutomationConstants.ValidatePin;
        Map<String,String> headers = new HashMap<String,String>();
        JsonObject postData = new JsonObject();
        postData.addProperty("phone_no", msisdn);
        HttpPostResponse response = postDataToUrl(accountUrl, "application/json", headers, postData.toString() , "POST");

        JsonObject data = (JsonObject)new JsonParser().parse(response.responseStr);

        if(response.responseCode != HttpURLConnection.HTTP_OK || data.get("stat")!=null && "fail".equalsIgnoreCase(data.get("stat").getAsString())) {
            throw new Exception("Error in sending pin request."+response.responseStr);
        }
    }

    public boolean delete(String msisdn) throws Exception {
        String accountUrl = "http://"+httphost+":"+httpport+AutomationConstants.DeleteAccountUrl;
        String token = mongo.getCurrentToken(msisdn, userDB);
        System.out.println(token);
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("Cookie", "user="+token);
        //HttpPostResponse response = HTTPServices.postDataToUrl(accountUrl, null, headers, "");
        HttpPostResponse response = getData(accountUrl, "application/json", headers, null,"DELETE");

        JsonObject data = (JsonObject)new JsonParser().parse(response.responseStr);

        if(response.responseCode == HttpURLConnection.HTTP_OK || data.get("stat")!=null && "ok".equalsIgnoreCase(data.get("stat").getAsString())) {
            return true;
        }
        return false;
    }

    public static HttpPostResponse getData(String urlStr, String contentType, Map<String,String> headers, String dataToPost, String requestMethod) throws Exception
    {
        URL url;
        HttpURLConnection connection;
        @SuppressWarnings("unused")
	DataOutputStream outStream;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
        } catch (MalformedURLException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        } catch (IOException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        }
        connection.setDoInput(true);
        //connection.setDoOutput(true);
        connection.setUseCaches(false);

        if(headers!=null){
            for(Entry<String,String> entry:headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        try {


            return new HttpPostResponse(connection.getResponseCode(),com.bsb.hike.util.IOUtil.readData(connection.getInputStream(), true));
        } catch (IOException ioe) {
            throw new Exception("Error occurred while doing io over the socket", ioe);
        } finally {
            connection.disconnect();
        }

    }

    public static HttpPostResponse postDataToUrl(String urlStr, String contentType, Map<String,String> headers, String dataToPost, String requestMethod) throws Exception
    {
        URL url;
        HttpURLConnection connection;
        DataOutputStream outStream;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
        } catch (MalformedURLException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        } catch (IOException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        }
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        connection.setRequestProperty("Content-Length", "" + dataToPost.length());
        if(StringUtils.isNotBlank(contentType)) {
            connection.setRequestProperty("Content-Type", "" + contentType);
        }
        if(headers!=null){
            for(Entry<String,String> entry:headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        try {
            outStream = new DataOutputStream(connection.getOutputStream());
            // Send request
            outStream.writeBytes(dataToPost);
            outStream.flush();

            return new HttpPostResponse(connection.getResponseCode(),com.bsb.hike.util.IOUtil.readData(connection.getInputStream(), true));
        } catch (IOException ioe) {
            throw new Exception("Error occurred while doing io over the socket", ioe);
        } finally {
            connection.disconnect();
        }
    }

    public static HttpPostResponse postFileToURL(String urlStr, String contentType, Map<String,String> headers, String fileToPost) throws Exception
    {
        URL url;
        HttpURLConnection connection;
        DataOutputStream outStream;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
        } catch (MalformedURLException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        } catch (IOException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        }
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        File file=new File(fileToPost);
        connection.setRequestProperty("Content-Length", "" + file.length());
        if(StringUtils.isNotBlank(contentType)) {
            connection.setRequestProperty("Content-Type", "" + contentType);
        }
        if(headers!=null){
            for(Entry<String,String> entry:headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        try {

            outStream = new DataOutputStream(connection.getOutputStream());
            // Send request
            byte[] data=new byte[(int)file.length()];
            new FileInputStream(file).read(data);
            outStream.write(data);
            outStream.flush();

            return new HttpPostResponse(connection.getResponseCode(),com.bsb.hike.util.IOUtil.readData(connection.getInputStream(), true));
        } catch (IOException ioe) {
            throw new Exception("Error occurred while doing io over the socket", ioe);
        } finally {
            connection.disconnect();
        }
    }


    public static HttpPostResponse getDataFromConsole(String urlStr)
	{
		URL url;
		HttpURLConnection connection = null;
		try
    {
        url = new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        url = uri.toURL();
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
    } catch (Exception e) {
			System.out.println("Exception cause:"+e.getCause());
			e.printStackTrace();
		}
		String basicAuth = "hikeStagingAPI:passHikeConsole";
		String auth = "Basic " + new String(Base64.encodeBase64(basicAuth.getBytes()));
		connection.setRequestProperty("Authorization",auth);
		connection.setDoInput(true);
		connection.setDoOutput(false);
		connection.setUseCaches(false);
		try
		{
			return new HttpPostResponse(connection.getResponseCode(),com.bsb.hike.util.IOUtil.readData(connection.getInputStream(), true));
		} catch (IOException ioe) {
			System.out.println("Exception cause:"+ioe.getCause());
			ioe.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return null;
	}

	public static HttpPostResponse deleteAccountCompletely(String urlStr, String contentType) throws Exception {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
		} catch (MalformedURLException e) {

			throw new Exception("Error occurred while creating httpconnection", e);
		} catch (IOException e) {

			throw new Exception("Error occurred while creating httpconnection", e);
		}
		String basicAuth = "hikeStagingAPI:passHikeConsole";
		String auth = "Basic " + new String(Base64.encodeBase64(basicAuth.getBytes()));
		connection.setRequestProperty("Authorization", auth);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		try {
			return new HttpPostResponse(connection.getResponseCode(),
					com.bsb.hike.util.IOUtil.readData(connection.getInputStream(), true));
		} catch (IOException ioe) {
			throw new Exception("Error occurred while doing io over the socket", ioe);
		} finally {
			connection.disconnect();
		}

	}

    public static class HttpPostResponse {
        public int responseCode;
        public String responseStr;

        public HttpPostResponse(int responseCode, String responseStr) {
            this.responseCode = responseCode;
            this.responseStr = responseStr;
        }
    }
}

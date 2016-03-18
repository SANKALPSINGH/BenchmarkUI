package com.bsb.hike.qa.httpservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HTTPServices {
	
    public static HttpPostResponse postDataToUrl(String urlStr, String contentType, Map<String,String> headers, String dataToPost) throws Exception
    {
        return postDataToUrl(urlStr, contentType, headers, dataToPost, "POST");
    }
    

	public static JSONObject getResponse(InputStream is) throws IOException, ParseException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		CharBuffer target = CharBuffer.allocate(10000);
		int read = reader.read(target);
		while (read >= 0)
		{
			builder.append(target.array(), 0, read);
			target.clear();
			read = reader.read(target);
		}
		try
		{
			new JSONParser().parse(new String(builder.toString()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
    
    public static HttpPostResponse get(String urlStr) throws Exception
    {
        return postDataToUrl(urlStr, null, null, "", "GET");
    }
    
    public static HttpPostResponse getData(String urlStr, String contentType, Map<String,String> headers, String dataToPost, String requestMethod) throws Exception
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

        //connection.setRequestProperty("Content-Length", "" + dataToPost.length());
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
    
    
    public static HttpPostResponse patchDataToUrl(String urlStr, String contentType, Map<String,String> headers, String dataToPost, String requestMethod) throws Exception
    {
    	
        URL url;
        HttpURLConnection connection;
        DataOutputStream outStream;
        
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PATCH");
        } catch (MalformedURLException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        } catch (IOException e) {

            throw new Exception("Error occurred while creating httpconnection", e);
        } 
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        
        connection.setRequestProperty("X-Http-Method-Override", "patch");

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
    
    public static HttpPostResponse putFile(String urlStr, String contentType, Map<String,String> headers, String fileToPost) throws Exception
    {
        URL url;
        HttpURLConnection connection;
        DataOutputStream outStream;
        
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
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
    
    
    public static HttpPostResponse deleteAccountCompletely(String urlStr, String contentType, Map<String,String> headers) throws Exception
    {
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
        connection.setRequestProperty("Authorization",auth);
        connection.setDoInput(true);
        connection.setDoOutput(true);
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

    public static class HttpPostResponse {
        public int responseCode;
        public String responseStr;
        
        public HttpPostResponse(int responseCode, String responseStr) {
            this.responseCode = responseCode;
            this.responseStr = responseStr;
        }
    }
    
}

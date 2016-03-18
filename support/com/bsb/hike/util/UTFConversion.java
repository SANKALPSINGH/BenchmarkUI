package com.bsb.hike.util;


public class UTFConversion
{
	public static String afterEncoding = "";
	public static String finalEncodedMessage = "";
	
	
    public static String convert(String messageToEncrypt){
        try{
        	
        	 finalEncodedMessage = new String(messageToEncrypt.getBytes("UTF8"));
              	
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return finalEncodedMessage;
    }
    
    public static String textConversion(String value){
    	try {  		
    	     afterEncoding = new String(value.getBytes("UTF-8"),"ISO-8859-1");
    	        	       	    	     
		} catch (Exception e) {
			// TODO: handle exception
		}
		return afterEncoding;
		   
    }

}

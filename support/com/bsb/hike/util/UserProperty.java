/**
 * 
 */
package com.bsb.hike.util;

/**
 * @author amita
 *
 */
public class UserProperty {
	private String msisdn;
	private String uid;
	private String token;
	
	public UserProperty(String msisdn, String uid, String token) {
		super();
		this.msisdn = msisdn;
		this.uid = uid;
		this.token = token;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}

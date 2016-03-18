package com.bsb.hike.qa.apisupport;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import com.bsb.hike.base.BaseClass;
import com.bsb.hike.core.HttpCore;
import com.bsb.hike.qa.httpservice.HTTPServices.HttpPostResponse;

public class UserModificationSupport extends BaseClass{
	
	HttpCore httpCore = new HttpCore();
public static void main(String a[])
{
	UserModificationSupport us = new UserModificationSupport();
	us.createHikeUserWithMsisdn("+911231231242");
	us.deleteUserCompletely("+911231231242");
}

	public boolean createHikeUserWithMsisdn(String msisdn){
		boolean isUserCreated = false;
		try {
			  httpCore.createUser(msisdn);
     	      String uid = getuidFromMsisdn(msisdn);
		      System.out.println(uid);
		      System.out.println("Is account created?: " + !StringUtils.isBlank(uid));
		      Thread.sleep(200);
		      isUserCreated = !StringUtils.isBlank(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUserCreated;
	}
	
	public boolean deleteUser(String msisdn){
		boolean isDeleted = false;
		try {
			HttpPostResponse response = httpCore.deleteAccount(msisdn);
			Assert.assertTrue("Error!! Account is not deleted",response.responseCode==200);
			if(response.responseCode==200)
				isDeleted=true;
			else
				isDeleted = false;
			System.out.println("Is account deleted: " + isDeleted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	public boolean deleteUserCompletely(String msisdn)
	{
		HttpPostResponse response = httpCore.deleteAcountCompletely(msisdn);
		Assert.assertTrue("Error!! Account is not deleted",response.responseCode==200);
		if(response.responseCode==200)
			return true;
		else
			 return false;
	}
}

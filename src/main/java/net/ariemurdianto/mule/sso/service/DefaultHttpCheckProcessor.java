package net.ariemurdianto.mule.sso.service;

import net.ariemurdianto.mule.sso.ConstantCollection;
import net.ariemurdianto.mule.sso.JsonUtil;
import net.ariemurdianto.mule.sso.entity.SsoUser;
import net.ariemurdianto.mule.sso.manager.UserManager;

public class DefaultHttpCheckProcessor {
	
	private UserManager userManager;
	public Object checkCredential(String parameters)
	{
		if(userManager.getNumberOfUser(parameters) != 0)
		{
			SsoUser user = userManager.getLocalUser(parameters);
			String returnValue = ConstantCollection.PROPERTY_USERNAME + "="
			+ user.getUsername() + "\n"
			+ ConstantCollection.PROPERTY_PASSWORD + "="
			+ user.getPassword() + "\n"
			+ ConstantCollection.PROPERTY_USER_EXISTS+"=yes";
			return returnValue;
		}
		else
		{
			return ConstantCollection.PROPERTY_USER_EXISTS+"=no";
		}
	}
	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}
	public UserManager getUserManager()
	{
		return this.userManager;
	}
}

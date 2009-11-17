package net.ariemurdianto.mule.sso.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.ariemurdianto.mule.sso.ConstantCollection;
import net.ariemurdianto.mule.sso.manager.UserManager;

public class DefaultHttpUpdateProcessor {
	private UserManager userManager;
	public Object updateCredential(String parameters)
	{
		String[] usernamePassword = parameters.split(ConstantCollection.PARAMETER_SEPARATOR);
		System.out.println("size of usernamePassword: "+usernamePassword.length);
		if(usernamePassword.length > 2)
		{
			return Calendar.getInstance().getTime().toString()+" --- "+ConstantCollection.ERR_PARAMETER_INVALID+"\n";
		}
		Map<String, String> map = new HashMap<String, String>();
		String toReturn = "";
		if(usernamePassword.length == 1){
			map.put("username", usernamePassword[0]);
			toReturn = "delete"+map.toString();
			 System.out.println("toReturn: "+toReturn);
			return toReturn; 
		}
		
		try
		{
			map.put("username", usernamePassword[0]);
			map.put("password", usernamePassword[1]);
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		toReturn = "insert"+map.toString();
		System.out.println("toReturn: "+toReturn);
		return toReturn;
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

package net.ariemurdianto.mule.sso.service;

import java.util.HashMap;
import java.util.Map;

public class DefaultSimpleReturn {
	public String simpleReturn(String stringToReturn)
	{
		System.out.println("this is the stringToReturn: "+stringToReturn);
		return stringToReturn;
	}

}

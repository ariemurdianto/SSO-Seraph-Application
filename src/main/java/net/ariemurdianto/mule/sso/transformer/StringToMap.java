package net.ariemurdianto.mule.sso.transformer;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class StringToMap extends AbstractTransformer{

	@SuppressWarnings("unchecked")
	@Override
	protected Object doTransform(Object src, String encoding)
			throws TransformerException {
		Map toTransform = new HashMap();
		if(src instanceof String)
		{
			String stringOfMap = src.toString();
			stringOfMap = stringOfMap.replace("insert", "");
			stringOfMap = stringOfMap.replace("delete", "");
			stringOfMap = stringOfMap.substring(1, stringOfMap.length() - 1);
			System.out.println("this is the string of map: " + stringOfMap);
			String[] keyValues = stringOfMap.split(",");
			for(int i=0; i<keyValues.length; i++)
			{
				//The length must be 2, otherwise it is wrong
				keyValues[i] = keyValues[i].trim();
				String[] keyValue = keyValues[i].split("=");
				toTransform.put(keyValue[0], keyValue[1]);
			}
		}
		return toTransform;
	}

}

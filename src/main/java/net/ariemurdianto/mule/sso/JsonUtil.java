package net.ariemurdianto.mule.sso;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.ariemurdianto.mule.sso.ConstantCollection;

import java.util.Vector;
import java.util.List;
import java.util.Hashtable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: arie
 * Date: Mar 23, 2009
 * Time: 9:53:58 AM
 * To change this template use File | Settings | File Templates.
 */
/**
 * Utility class to convert Java object to JSON string and vice versa
 */
public class JsonUtil {
    /**
     * Convert Java object to String. Before going to this method, all the characters which cause the problem
     * should be escaped so it wont crash this method and no ruin the JSON rule.
     */
    public static String convertObjectToJson(Object javaObject) {
        if (javaObject instanceof String) {
            return "\"" + javaObject.toString() + "\"";
        }
        if (javaObject.getClass().isArray() || javaObject instanceof Vector || javaObject instanceof List) {
            JSONArray arrayOfResult = JSONArray.fromObject(javaObject);
            return arrayOfResult.toString();
        }
        return JSONObject.fromObject(javaObject).toString();
    }

    /**
     * Convert JSON string to Java object
     */
    public static Object convertJsonStringToObject(String jsonString, Class objectType) {
		Object castedResult = null;

		if (objectType.getName().equals(Integer.class.getName())) {
			castedResult = new Integer(jsonString);
		} else if (objectType.getName().equals(Long.class.getName())) {
			castedResult = new Long(jsonString);
		} else if (objectType.getName().equals(Double.class.getName())) {
			castedResult = new Double(jsonString);
		} else if (objectType.getName().equals(int.class.getName())) {
			castedResult = new Integer(jsonString).intValue();
		} else if (objectType.getName().equals(long.class.getName())) {
			castedResult = new Long(jsonString).longValue();
		} else {
			castedResult = jsonString;
		}
        return castedResult;
    }


}

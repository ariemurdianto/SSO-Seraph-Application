package net.ariemurdianto.mule.sso;

public interface ConstantCollection {
	final static String PARAMETER_SEPARATOR = ",";//this is representing koma
	final static String CHECK_PATH = "/services/checkCredential";
	final static String INSERT_PATH = "/services/updateCredential";
	final static String PROPERTIES_FILENAME = "configuration-sso.properties";
		
	final static String COLUMN_USERNAME = "username";
	final static String COLUMN_PASSWORD = "password";
	
	final static String PROPERTY_USERNAME = "sso.user.username";
	final static String PROPERTY_PASSWORD = "sso.user.password";
	final static String PROPERTY_USER_EXISTS = "sso.user.exist";
	final static String ERR_PARAMETER_INVALID = "INVALID PARAMETER";
	final static String SUCCESS_INSERTED = "sso.user.inserted";
	final static String FAILED_AUTHENTICATED = "FAILED TO AUTHENTICATE";
}

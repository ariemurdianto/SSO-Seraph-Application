package net.ariemurdianto.mule.sso.authenticator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import com.atlassian.seraph.cookie.CookieHandler;

/**
 * Created by IntelliJ IDEA.
 * User: arie
 * Date: Nov 15, 2009
 * Time: 4:12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CustomAuthenticator {
    public static final String PROPERTIES_FILE = "sso-config.properties";
    public static final String KEY_MULE_URL = "sso.server.url";

    public static final String COOKIES_NAME = "ariemurdianto_sso";

    public static final String KEY_USER_EXIST = "sso.user.exist";
    public final static String PROPERTY_USERNAME = "sso.user.username";
    public final static String PROPERTY_PASSWORD = "sso.user.password";

    public static final String HOST = "localhost";
    public static final String PORT_CHECK = "8123";
    public static final String PORT_UPDATE = "8124";
    
    public static final String DEFAULT_CHECK_URL = "http://"+HOST+":"+PORT_CHECK+"/services/checkCredential";
    public static final String DEFAULT_INSERT_URL = "http://"+HOST+":"+PORT_UPDATE+"/services/updateCredential";
    public static final String PARAMETER_NAME = "parameters";
    public static final String USERNAME_ATTRIBUTE = "os_username";


    public final static String SUCCESS_AUTHENTICATED = "SUCCESSFULLY AUTHENTICATE";
    public final static String FAILED_AUTHENTICATED = "FAILED TO AUTHENTICATE";

    
    
}

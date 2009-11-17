package com.atlassian.seraph.filter;

import com.atlassian.seraph.RequestParameterConstants;
import com.atlassian.seraph.cookie.CookieHandler;
import com.atlassian.seraph.cookie.CookieFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import net.ariemurdianto.mule.sso.authenticator.JiraCustomAuthenticator;

/**
 * Created by IntelliJ IDEA.
 * User: arie
 * Date: Nov 14, 2009
 * Time: 3:42:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomLoginFilter extends LoginFilter
{
    private static final String COOKIES_NAME = "ariemurdianto_sso";
    private static final String DEFAULT_CHECK_URL = "http://localhost:8123/services/checkCredential";
    private final static String PROPERTY_USERNAME = "sso.user.username";
	private final static String PROPERTY_PASSWORD = "sso.user.password";

    private static final Logger log = Logger.getLogger(CustomLoginFilter.class);
    
// well, I have no option beside overriding the LoginFilter.java in Seraph. Seraph is gonna
// is going to perform authentication when parameter os_username and os_password being thrown to the server
// Thus, i should override it so that the UserPasswordPair object in PasswordBasedLoginFilter.java
// is not null and the authentication to perform the sso is completed.
    
	public UserPasswordPair extractUserPasswordPair(HttpServletRequest request)
	{
        // check for parameters
        String username = request.getParameter(RequestParameterConstants.OS_USERNAME);
        String password = request.getParameter(RequestParameterConstants.OS_PASSWORD);
        CookieHandler cookiesHandler = CookieFactory.getCookieHandler();
        Cookie cookies = cookiesHandler.getCookie(request, COOKIES_NAME);

        if(cookies != null)
        {
            try
            {
// To contact the middleware               
                HttpClient httpClient = new HttpClient();
                GetMethod getMethod = new GetMethod(DEFAULT_CHECK_URL);
                log.error("the values of cookies: "+ cookies.getValue());
                NameValuePair[] parameters = JiraCustomAuthenticator.getParameters(cookies.getValue());
                getMethod.setQueryString(parameters);

                httpClient.executeMethod(getMethod);
                InputStream inputStream = getMethod.getResponseBodyAsStream();
                Properties properties = JiraCustomAuthenticator.getPropertyValueFromStream(inputStream);
                username = properties.getProperty(PROPERTY_USERNAME);
                password = properties.getProperty(PROPERTY_PASSWORD);
                
                if(log.isDebugEnabled())
                {
                    log.debug("FETCHING USERNAME "+username+" AND PASSWORD "+password);
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        boolean persistentLogin = "true".equals(request.getParameter(RequestParameterConstants.OS_COOKIE));
        return new UserPasswordPair(username, password, persistentLogin);
	}
}
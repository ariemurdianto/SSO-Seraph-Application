package com.atlassian.seraph.filter;

import com.atlassian.seraph.RequestParameterConstants;
import com.atlassian.seraph.cookie.CookieHandler;
import com.atlassian.seraph.cookie.CookieFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.*;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.Properties;

import net.ariemurdianto.mule.sso.authenticator.JiraCustomAuthenticator;
import net.ariemurdianto.mule.sso.authenticator.CustomAuthenticator;

/**
 * Created by IntelliJ IDEA.
 * User: arie
 * Date: Nov 14, 2009
 * Time: 3:42:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomLoginFilter extends LoginFilter implements CustomAuthenticator {
    public MultiThreadedHttpConnectionManager connectionManager;

    private static final Logger log = Logger.getLogger(CustomLoginFilter.class);

// well, I have no option beside overriding the LoginFilter.java in Seraph. Seraph is gonna
// is going to perform authentication when parameter os_username and os_password being thrown to the server
// Thus, i should override it so that the UserPasswordPair object in PasswordBasedLoginFilter.java

    // is not null and the authentication to perform the sso is completed.

    public CustomLoginFilter() {
        connectionManager = new MultiThreadedHttpConnectionManager();
//        httpClient = new HttpClient(connectionManager);

//        HostConfiguration hostConfiguration = new HostConfiguration();
//        hostConfiguration.setHost(HOST);
//        httpClient.setHostConfiguration(hostConfiguration);
    }

    public PasswordBasedLoginFilter.UserPasswordPair extractUserPasswordPair(HttpServletRequest request) {
        // check for parameters
        String username = request.getParameter(RequestParameterConstants.OS_USERNAME);
        String password = request.getParameter(RequestParameterConstants.OS_PASSWORD);
        boolean persistentLogin = "true".equals(request.getParameter(RequestParameterConstants.OS_COOKIE));
        if (username == null && password == null) {
            CookieHandler cookiesHandler = CookieFactory.getCookieHandler();
            Cookie cookies = cookiesHandler.getCookie(request, COOKIES_NAME);
//            HostConfiguration hostConfiguration = httpClient.getHostConfiguration();
//            HttpConnection connection = httpClient.getHttpConnectionManager().getConnection(hostConfiguration);
            if (cookies != null) {
                try {
// To contact the middleware
                    log.error("the values of cookies: " + cookies.getValue());
                    NameValuePair[] parameters = JiraCustomAuthenticator.getParameters(cookies.getValue());
                    HttpClient httpClient = new HttpClient();
                    GetMethod getMethod = new GetMethod(DEFAULT_CHECK_URL);
                    getMethod.setQueryString(parameters);

                    System.out.println("connection status kali? " + httpClient.executeMethod(getMethod));
                    System.out.println("connection status text kali? " + getMethod.getStatusText());
                    if (getMethod.getResponseBodyAsStream() == null) {
                        System.out.println();
                    }
                    byte[] byteOfResponse= getMethod.getResponseBody();
                    InputStream inputStream = new ByteArrayInputStream(byteOfResponse);
                    Properties properties = JiraCustomAuthenticator.getPropertyValueFromStream(inputStream);
                    username = properties.getProperty(PROPERTY_USERNAME);
                    password = properties.getProperty(PROPERTY_PASSWORD);

                    if (log.isDebugEnabled()) {
                        log.debug("FETCHING USERNAME " + username + " AND PASSWORD " + password);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
//                    getMethod.releaseConnection();
//                connection.close();
                }
            }
        }
        return new PasswordBasedLoginFilter.UserPasswordPair(username, password, persistentLogin);
    }
}
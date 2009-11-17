package net.ariemurdianto.mule.sso.authenticator;

import com.atlassian.seraph.auth.AuthenticatorException;
import com.atlassian.seraph.cookie.CookieHandler;
import com.atlassian.seraph.cookie.CookieFactory;
import com.atlassian.confluence.user.ConfluenceAuthenticator;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

public class ConfluenceCustomAuthenticator extends ConfluenceAuthenticator implements CustomAuthenticator {
//    public HttpClient httpClient = null;
    public MultiThreadedHttpConnectionManager connectionManager;
//    public GetMethod getMethod = new GetMethod(DEFAULT_CHECK_URL);
    public CookieHandler cookiesHandler = null;
    public HttpServletRequest request;

    private static final Logger log = Logger.getLogger(JiraCustomAuthenticator.class);

    public ConfluenceCustomAuthenticator() {

//        connectionManager = new MultiThreadedHttpConnectionManager();
//        httpClient = new HttpClient(connectionManager);
////        httpClient = new HttpClient();
//        HostConfiguration hostConfiguration = new HostConfiguration();
//        hostConfiguration.setHost(HOST);
//        httpClient.setHostConfiguration(hostConfiguration);
    }

    public static Properties getPropertyValueFromStream(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            return properties;
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }

    public boolean login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String username, String password, boolean b) throws AuthenticatorException {
        cookiesHandler = CookieFactory.getCookieHandler();
        this.request = httpServletRequest;
        boolean isUserAllowed = super.login(httpServletRequest, httpServletResponse, username, password, b);
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(DEFAULT_INSERT_URL);
        if (isUserAllowed) {
            cookiesHandler.setCookie(httpServletRequest, httpServletResponse, COOKIES_NAME, username, 100000, "/");
            try {
                if (this.checkUsernameIfExist(username)) return isUserAllowed;

                String parametersToPass = username + "," + password;
                getMethod.setQueryString(getParameters(parametersToPass));

                httpClient.executeMethod(getMethod);
                String returnedUser = getMethod.getResponseBodyAsString();
                if (!returnedUser.contains(username)) {
                    System.out.println("user fails to be stored");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                getMethod.releaseConnection();
//            connection.close();
            }
        }
        return isUserAllowed;
    }

    public boolean checkUsernameIfExist(String username) throws IOException {
//        HostConfiguration hostConfiguration = httpClient.getHostConfiguration();
//        HttpConnection connection = httpClient.getHttpConnectionManager().getConnection(hostConfiguration);
        NameValuePair[] nameValuePair = this.getParameters(username);
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(DEFAULT_CHECK_URL);
        getMethod.setQueryString(nameValuePair);

        httpClient.executeMethod(getMethod);
        if (getMethod.getResponseBodyAsStream() == null) {
            String byteResponse = getMethod.getResponseBodyAsString();
            System.out.println(byteResponse);
        }
        InputStream inputStream = getMethod.getResponseBodyAsStream();
        Properties properties = this.getPropertyValueFromStream(inputStream);
        inputStream.close();
//        connection.close();
        getMethod.releaseConnection();
        if (properties.getProperty(ConfluenceCustomAuthenticator.KEY_USER_EXIST).equals("yes")) {
            return true;
        }
        return false;
    }

    public static NameValuePair[] getParameters(String parametersToPass) {
        NameValuePair[] parameters = new NameValuePair[1];
        parameters[0] = new NameValuePair();
        parameters[0].setName(PARAMETER_NAME);
        parameters[0].setValue(parametersToPass);
        return parameters;
    }

    protected boolean authenticate(Principal user, String password) {
        boolean isUserAllowed = super.authenticate(user, password);
        if (!isUserAllowed) return false;
//        HttpConnection connection = connectionManager.getConnection(httpClient.getHostConfiguration());
// if user exists, then we do not need to contact the sso server
        return isUserAllowed;
    }

    public boolean logout(HttpServletRequest request, HttpServletResponse response) throws AuthenticatorException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(CustomAuthenticator.DEFAULT_INSERT_URL);
        CookieHandler cookiesHandler = CookieFactory.getCookieHandler();
        Cookie cookies = cookiesHandler.getCookie(request, COOKIES_NAME);
        getMethod.setQueryString(getParameters(cookies.getValue()));
        try {
            if (log.isDebugEnabled())
                log.debug("DELETING USER ON THE MIDDLEWARE SERVER...");
            httpClient.executeMethod(getMethod);
        }
        catch (IOException io) {
            io.printStackTrace();
        }
        finally {
//            getMethod.releaseConnection();
        }
        boolean isLogout = super.logout(request, response);
        if (isLogout) {
            cookiesHandler.invalidateCookie(request, response, COOKIES_NAME, "/");
        }
        return isLogout;
    }
}

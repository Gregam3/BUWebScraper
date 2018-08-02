package com.bu.webscraping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class Login {
    private String username;
    private String password;
    private String loginUrl;
    private Map<String, String> cookies;

    public Login(String username, String password, String loginUrl) {
        this.username = username;
        this.password = password;
        this.loginUrl = loginUrl;
        this.cookies = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void addCookie(String cookieName, String cookie) {
        cookies.put(cookieName, cookie);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}

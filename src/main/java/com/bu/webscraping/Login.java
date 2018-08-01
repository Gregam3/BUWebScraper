package com.bu.webscraping;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class Login {
    private String username;
    private String password;
    private String loginUrl;

    public Login(String username, String password, String loginUrl) {
        this.username = username;
        this.password = password;
        this.loginUrl = loginUrl;
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
}

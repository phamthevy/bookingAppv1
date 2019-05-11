package com.bku.appbooking.ultis;

public class UserInfo {
    private static final UserInfo ourInstance = new UserInfo();
    private String mail;
    private String accessToken;
    private String name;

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public static UserInfo getInstance() {
        return ourInstance;
    }

    private UserInfo() {
    }

}

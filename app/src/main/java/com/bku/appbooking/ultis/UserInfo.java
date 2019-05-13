package com.bku.appbooking.ultis;

public class UserInfo {
    private static final UserInfo ourInstance = new UserInfo();
    private String mail;
    private String address;
    private String phone;
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
        mail = "";
        address = "";
        phone = "";
        accessToken = "";
        name = "";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

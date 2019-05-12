package com.bku.appbooking.ultis;

public class UserInfo {
    private static final UserInfo ourInstance = new UserInfo();
    private String mail;
    private String accessToken ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9ib29raW5nLnZpaGV5LmNvbSIsImF1ZCI6Imh0dHA6XC9cL2Jvb2tpbmcudmloZXkuY29tIiwiaWF0IjoxNTU3NjgwNDU5LCJuYmYiOjEzNTcwMDAwMDAsImV4cCI6MTU1NzY4MTA1OSwiZGF0YSI6eyJpZCI6IjMiLCJob3RlbiI6ImhvdGVuIiwic2R0IjoiMDcwNzA3MDcwNDQ0IiwiZW1haWwiOiJhc2FzZGFAZ21haWwuY29tIiwiZGlhY2hpIjpudWxsfX0.d-TTlgV7zIQK4ldzczlqOQdDomFaTBruN4sAna9x6Dw";
    private String name;
    private String address;
    private String phone;

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

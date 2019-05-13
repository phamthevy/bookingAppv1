package com.bku.appbooking.ultis;

import android.widget.Toast;

public class UserInfo {
    private static final UserInfo ourInstance = new UserInfo();
    private String mail;
    private String pass;
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

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
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
        pass = "";
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

    public void keepConnection(){
        if (mail.equals("") || pass.equals("")) {
            return;
        }// lam sau neu con thoi gian vi co the phai handle kill timer khi thoat app
    }
}

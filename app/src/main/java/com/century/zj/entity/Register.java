package com.century.zj.entity;

public class Register {
    private String password;
    private String username;
    private String userphone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public Register(String password, String username, String userphone) {
        this.password = password;
        this.username = username;
        this.userphone = userphone;
    }
}

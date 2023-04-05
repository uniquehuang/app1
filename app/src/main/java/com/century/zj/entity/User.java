package com.century.zj.entity;

public class User {
    private String userphone;
    private String password;

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String userphone, String password) {
        this.userphone = userphone;
        this.password = password;
    }
}

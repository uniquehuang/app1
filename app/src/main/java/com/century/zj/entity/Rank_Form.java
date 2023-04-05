package com.century.zj.entity;


public class Rank_Form {
    private int code;
    private String username;
    private int gender;
    private String userimg;

    public Rank_Form(int code, String username, int gender, String userimg) {
        this.code = code;
        this.username = username;
        this.gender = gender;
        this.userimg = userimg;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}

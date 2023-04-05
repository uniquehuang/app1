package com.century.zj.entity;

public class CollectAttraction {
    private String id;
    private String title;
    private String userphone;
    private int scflag;
    private String imgurl;
    private String text;

    public CollectAttraction(String userphone, String title, int scflag, String imgurl) {
        this.title = title;
        this.userphone = userphone;
        this.scflag = scflag;
        this.imgurl = imgurl;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public int getScflag() {
        return scflag;
    }

    public void setScflag(int scflag) {
        this.scflag = scflag;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}

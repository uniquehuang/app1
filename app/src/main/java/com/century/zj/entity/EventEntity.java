package com.century.zj.entity;

import java.net.URI;

public class EventEntity {
    private int id;
    private String imgurl;
    private String title;
    private String category;
    private String place;
    private String article;
    private String anotherimgurl;
    private String anotherarticle;
    private String audiourl;
    private String readflag;

    public String getReadflag() {
        return readflag;
    }

    public void setReadflag(String readflag) {
        this.readflag = readflag;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getAnotherimgurl() {
        return anotherimgurl;
    }

    public void setAnotherimgurl(String anotherimgurl) {
        this.anotherimgurl = anotherimgurl;
    }

    public String getAnotherarticle() {
        return anotherarticle;
    }

    public void setAnotherarticle(String anotherarticle) {
        this.anotherarticle = anotherarticle;
    }

    public String getAudiourl() {
        return audiourl;
    }

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

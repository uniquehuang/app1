package com.century.zj.entity;

import java.io.Serializable;

public class WorkEntity implements Serializable {
    private int id;
    private String imgurl;
    private String title;
    private String author;
    private String category;
    private String article;
    private String anotherimgurl;
    private String anotherarticle;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public WorkEntity(String imgurl, String title, String author, String category) {
        this.imgurl = imgurl;
        this.title = title;
        this.author = author;
        this.category = category;
    }
}

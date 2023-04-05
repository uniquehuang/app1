package com.century.zj.entity;

import java.io.Serializable;

public class PersonEntity implements Serializable {
    private int id;
    private String title;
    private String age;
    private String text;
    private String imgurl;
    private String sort;
    private String article;
    private String anotherimgurl;
    private String anotherarticle;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public PersonEntity(String title, String age, String text, String imgurl) {
        this.title = title;
        this.age = age;
        this.text = text;
        this.imgurl = imgurl;
    }
}

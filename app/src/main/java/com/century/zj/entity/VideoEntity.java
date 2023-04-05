package com.century.zj.entity;

import java.io.Serializable;

public class VideoEntity implements Serializable {
    private int id;
    private String title;
    private String name;
    private int dzcount;
    private int collectcount;
    private int commentcount;
    private String playurl;
    private String imgurl;
    private String userimg;
    private String sort;
    private int dzflag;
    private int scflag;
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDzcount() {
        return dzcount;
    }

    public void setDzcount(int dzcount) {
        this.dzcount = dzcount;
    }

    public int getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(int collectcount) {
        this.collectcount = collectcount;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {

        this.playurl = playurl;
    }

    public int getDzflag() {
        return dzflag;
    }

    public void setDzflag(int dzflag) {
        this.dzflag = dzflag;
    }

    public int getScflag() {
        return scflag;
    }

    public void setScflag(int scflag) {
        this.scflag = scflag;
    }
}

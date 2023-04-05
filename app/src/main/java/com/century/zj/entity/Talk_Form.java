package com.century.zj.entity;

public class Talk_Form {
    int id;
    String username;
    String userphone;
    String imgurl1;
    String imgurl2;
    String imgurl3;
    int dzcount;
    String posttime;
    String article;
    String userimg;
    int dzflag;

    public Talk_Form(int id, String username, String userphone, String imgurl1, String imgurl2, String imgurl3, int dzcount, String posttime, String article, String userimg, int dzflag) {
        this.id = id;
        this.username = username;
        this.userphone = userphone;
        this.imgurl1 = imgurl1;
        this.imgurl2 = imgurl2;
        this.imgurl3 = imgurl3;
        this.dzcount = dzcount;
        this.posttime = posttime;
        this.article = article;
        this.userimg = userimg;
        this.dzflag = dzflag;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public int getDzflag() {
        return dzflag;
    }

    public void setDzflag(int dzflag) {
        this.dzflag = dzflag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImgurl1() {
        return imgurl1;
    }

    public void setImgurl1(String imgurl1) {
        this.imgurl1 = imgurl1;
    }

    public String getImgurl2() {
        return imgurl2;
    }

    public void setImgurl2(String imgurl2) {
        this.imgurl2 = imgurl2;
    }

    public String getImgurl3() {
        return imgurl3;
    }

    public void setImgurl3(String imgurl3) {
        this.imgurl3 = imgurl3;
    }

    public int getDzcount() {
        return dzcount;
    }

    public void setDzcount(int dzcount) {
        this.dzcount = dzcount;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}

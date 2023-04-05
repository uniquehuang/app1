package com.century.zj.entity;

public class RedThings_Form {
    private int id;
    private String title;
    private String place;
    private String category;
    private String imgurl;

    public RedThings_Form(int id, String title, String place, String category, String imgurl) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.category = category;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}

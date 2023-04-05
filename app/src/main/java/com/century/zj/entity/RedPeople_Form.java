package com.century.zj.entity;

public class RedPeople_Form {
    private int id;
    private String title;
    private String age;
    private String text;
    private String imgurl;

    public RedPeople_Form(int id, String title, String age, String text, String imgurl) {
        this.id = id;
        this.title = title;
        this.age = age;
        this.text = text;
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
}

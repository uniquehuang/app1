package com.century.zj.entity;

public class User_Form {
    /**
     * userphone:电话
     * password:密码
     * username:用户名
     * gender:性别 1男；0女
     * code:积分
     * pkcode:pk积分
     * replycode：答题积分
     * level：拼图难度
     * drawright：画画的
     * dzcount：点赞
     * studydayscount：打卡
     */
    private String userphone;
    private String password;
    private String username;
    private String userimg;
    private int readnumber;
    private int pkcode;
    private int replycode;
    private int level;
    private int drawright;
    private int dzcount;
    private int studydayscount;
    private int studytimecount;
    private int gender;
    private int code;
    private int id;

    public User_Form(String userphone, String password, String username, String userimg, int readnumber, int pkcode, int replycode, int level, int drawright, int dzcount, int studydayscount, int studytimecount, int gender, int code, int id) {
        this.userphone = userphone;
        this.password = password;
        this.username = username;
        this.userimg = userimg;
        this.readnumber = readnumber;
        this.pkcode = pkcode;
        this.replycode = replycode;
        this.level = level;
        this.drawright = drawright;
        this.dzcount = dzcount;
        this.studydayscount = studydayscount;
        this.studytimecount = studytimecount;
        this.gender = gender;
        this.code = code;
        this.id = id;
    }

    public int getReadnumber() {
        return readnumber;
    }

    public void setReadnumber(int readnumber) {
        this.readnumber = readnumber;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPkcode() {
        return pkcode;
    }

    public void setPkcode(int pkcode) {
        this.pkcode = pkcode;
    }

    public int getReplycode() {
        return replycode;
    }

    public void setReplycode(int replycode) {
        this.replycode = replycode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDrawright() {
        return drawright;
    }

    public void setDrawright(int drawright) {
        this.drawright = drawright;
    }

    public int getDzcount() {
        return dzcount;
    }

    public void setDzcount(int dzcount) {
        this.dzcount = dzcount;
    }

    public int getStudydayscount() {
        return studydayscount;
    }

    public void setStudydayscount(int studydayscount) {
        this.studydayscount = studydayscount;
    }

    public int getStudytimecount() {
        return studytimecount;
    }

    public void setStudytimecount(int studytimecount) {
        this.studytimecount = studytimecount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

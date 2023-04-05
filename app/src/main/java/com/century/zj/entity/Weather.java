package com.century.zj.entity;

public class Weather {
    //{"cityid":"101210307","city":"南湖","update_time":"17:30","wea":"多云","wea_img":"yun","tem":"30",
    // "tem_day":"31","tem_night":"24","win":"北风","win_speed":"1级","win_meter":"2km\/h","air":"48"}

    String cityid;

    String city;

    String wea;

    String tem;

    String win;

    String win_speed;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }
}

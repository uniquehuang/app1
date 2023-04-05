package com.century.zj.entity;

public class ReadEntity {
    /**
     * {
     * "eventid": 0,
     * "readflag": 0,
     * "userphone": "string"
     * }
     */
    private int eventid;
    private int readflag;
    private String userphone;

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public int getReadflag() {
        return readflag;
    }

    public void setReadflag(int readflag) {
        this.readflag = readflag;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public ReadEntity(int eventid, int readflag, String userphone) {
        this.eventid = eventid;
        this.readflag = readflag;
        this.userphone = userphone;
    }
}

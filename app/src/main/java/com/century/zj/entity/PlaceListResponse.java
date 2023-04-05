package com.century.zj.entity;

import java.util.List;

public class PlaceListResponse {

    private int code;
    private String msg;
    private List<PlaceEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PlaceEntity> getList() {
        return data;
    }

    public void setData(List<PlaceEntity> data) {
        this.data = data;
    }


}

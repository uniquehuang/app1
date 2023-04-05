package com.century.zj.entity;

import java.util.List;

public class ResponseRank {
    private String code;
    private String msg;
    private List<Rank_Form> data;

    public ResponseRank(String code, String msg, List<Rank_Form> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Rank_Form> getData() {
        return data;
    }

    public void setData(List<Rank_Form> data) {
        this.data = data;
    }
}

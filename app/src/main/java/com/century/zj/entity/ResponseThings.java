package com.century.zj.entity;

public class ResponseThings {
    /**
     * msg : 操作成功
     * code : 200
     * data : {
     * ....
     * }
     */

    private String code;
    private String msg;
    private RedThings_Form data;

    public ResponseThings(String code, String msg, RedThings_Form data) {
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

    public RedThings_Form getData() {
        return data;
    }

    public void setData(RedThings_Form data) {
        this.data = data;
    }
}

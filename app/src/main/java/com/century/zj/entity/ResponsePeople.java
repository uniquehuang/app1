package com.century.zj.entity;

public class ResponsePeople {
    /**
     * msg : 操作成功
     * code : 200
     * data : {
     * ....
     * }
     */

    private String code;
    private String msg;
    private RedPeople_Form data;

    public ResponsePeople(String code, String msg, RedPeople_Form data) {
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

    public RedPeople_Form getData() {
        return data;
    }

    public void setData(RedPeople_Form data) {
        this.data = data;
    }
}

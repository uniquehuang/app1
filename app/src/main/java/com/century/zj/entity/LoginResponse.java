package com.century.zj.entity;


public class LoginResponse {

    /**
     * msg : 操作成功
     * code : 200
     * data : {
     * ....
     * }
     */

    private String code;
    private String msg;
    private User_Form data;

    public LoginResponse(String code, String msg, User_Form data) {
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

    public User_Form getData() {
        return data;
    }

    public void setData(User_Form data) {
        this.data = data;
    }
}



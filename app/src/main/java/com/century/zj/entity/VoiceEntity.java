package com.century.zj.entity;

public class VoiceEntity {
    /**
     * {"code":1,
     * "url":"https://reptile.akeyn.com/audio/162997555764160.mp3",
     * "tips":"每天凌晨自动清除前一天的数据"}
     */
    private int code;
    private String url;
    private String tips;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}

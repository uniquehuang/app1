package com.century.zj.ui.main.Home_page;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlImage {
    public String url;
    public UrlImage(String url) {
        this.url = url;
    }
    public Bitmap getUrlImage(){
        Bitmap bmp = null;
        try {
            URL myUrl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}

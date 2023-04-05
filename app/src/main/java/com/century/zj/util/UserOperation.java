package com.century.zj.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.century.zj.api.Api;
import com.century.zj.api.TtitCallback;
import com.century.zj.entity.Rank_Form;
import com.century.zj.entity.ResponseRank;
import com.century.zj.entity.User_Form;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class UserOperation {
    private static HashMap<String, Object> UserParams;
    public static UserOperation userOperation = new UserOperation();
    private List<Rank_Form> list=new ArrayList<>();

    public static UserOperation UserConfig(int UserId, HashMap<String, Object> mParams) {
        mParams.put("id", UserId);
        UserParams = mParams;
        return userOperation;
    }

    //修改用户信息
    public void UserInformation(Context context, String url) {
        Api api=new Api();
        Api.config(api.getAppendUrl(url,UserParams), UserParams).postRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Log.e("QQQQQQQQQQQQQq", res);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //增加他人点赞数
    public void OtherInformationDz(Context context, String url) {

        Api.config(url, UserParams).postRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void initList(Context context){
        String url="/user/getUserCodeList";
        HashMap<String, Object> params=new HashMap<>();
        Api.config(url, params).getRequest(context, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Gson gson=new Gson();
                ResponseRank responseRank=gson.fromJson(res,ResponseRank.class);
                SharedPreferences shp = context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shp.edit();
                if (responseRank.getCode().equals("200")){
                    List<Rank_Form> listR=responseRank.getData();
                    String s=gson.toJson(listR);
                    editor.putString("KEY_USER_RANK",s);
                }
                editor.apply();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void addAll(User_Form user_form, Context context) {
        SharedPreferences shp = context.getSharedPreferences("User_Tab", MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        //总积分
        editor.putInt(UserConfigForm.UGA, user_form.getCode());
        //答题积分
        editor.putInt(UserConfigForm.UR, user_form.getReplycode());
        //PK积分
        editor.putInt(UserConfigForm.UPK, user_form.getPkcode());
        //拼图难度
        editor.putInt(UserConfigForm.UL, user_form.getLevel());
        //画画
        editor.putInt(UserConfigForm.UDRAW, user_form.getDrawright());
        //打卡
        editor.putInt(UserConfigForm.USD, user_form.getStudydayscount());
        //学习时间
        editor.putInt(UserConfigForm.UST, user_form.getStudytimecount());
        //点赞
        editor.putInt(UserConfigForm.UDZ, user_form.getDzcount());
        editor.apply();
    }
}

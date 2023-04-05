package com.century.zj.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.century.zj.entity.Rank_Form;
import com.century.zj.entity.Talk_Form;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class SaveTool {
    private final Context context;
    Gson gson = new Gson();
    public SaveTool(Context context) {
        this.context = context;
    }

    public void writeForum(List<Talk_Form> data){
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Forum",gson.toJson(data));
        editor.apply();
    }

    public List<Talk_Form> readForum(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("Forum", "");
        return new ArrayList<>(gson.fromJson(s, new TypeToken<List<Talk_Form>>() {
        }.getType()));
    }

    public void writeSingleForum(Talk_Form data){
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("SingleForum",gson.toJson(data));
        editor.apply();
    }

    public Talk_Form readSingleForum(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("SingleForum", "");
        return gson.fromJson(s, new TypeToken<List<Talk_Form>>() {
        }.getType());
    }

    public void writeRank(List<Rank_Form> data){
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("KEY_USER_RANK",gson.toJson(data));
        editor.apply();
    }

    public List<Rank_Form> readRank(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("User_List", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("KEY_USER_RANK", "");
        List<Rank_Form> list=gson.fromJson(s, new TypeToken<List<Rank_Form>>(){}.getType());
        return list;
    }

}

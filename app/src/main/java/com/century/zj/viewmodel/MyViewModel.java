package com.century.zj.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.century.zj.R;
import com.century.zj.entity.User_Form;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {
    private final SavedStateHandle handle;
    //用户ID
    private final String key_id = "id";
    //    用户账号
    private final String key_phone = getApplication().getResources().getString(R.string.USER_PHONE);
    //    用户密码
    private final String key_password = getApplication().getResources().getString(R.string.PASSWORD);
    //    用户名
    private final String key_name = getApplication().getResources().getString(R.string.USER_NAME);
    //    用户性别
    private final String key_gender = getApplication().getResources().getString(R.string.USER_GENDER);
    //    用户积分
    private final String key_grade = getApplication().getResources().getString(R.string.USER_GRADE);
    //    登录状态 1登录，0未登录
    private final String KEY_OUT_IN = getApplication().getResources().getString(R.string.OUT_IN);
    //    点赞数
    private final String key_dz = getApplication().getResources().getString(R.string.USER_DZ);
    //    学习天数
    private final String key_study_day = getApplication().getResources().getString(R.string.USER_STUDY_DAY);
    //    学习时长
    private final String key_study_time = getApplication().getResources().getString(R.string.USER_STUDY_TIME);
    //    PK积分
    private final String key_pk = getApplication().getResources().getString(R.string.USER_PK);
    //    答题积分
    private final String key_reply = getApplication().getResources().getString(R.string.USER_REPLY);
    //    拼图难度
    private final String key_level = getApplication().getResources().getString(R.string.USER_LEVEL);
    //    画画
    private final String key_draw = getApplication().getResources().getString(R.string.USER_DRAW);
    //
    private final String key_userimg = getApplication().getResources().getString(R.string.USER_IMG);

    private final String key_Code=getApplication().getResources().getString(R.string.USER_CODE);

    //readnumber
    private final String key_read = getApplication().getResources().getString(R.string.USER_READ);


    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        boolean b = handle.contains(key_phone)
                && handle.contains(key_password)
                && handle.contains(key_name)
                && handle.contains(key_gender)
                && handle.contains(key_grade)
                && handle.contains(KEY_OUT_IN)
                && handle.contains(key_dz)
                && handle.contains(key_study_day)
                && handle.contains(key_study_time)
                && handle.contains(key_pk)
                && handle.contains(key_reply)
                && handle.contains(key_level)
                && handle.contains(key_draw)
                && handle.contains(key_id)
                && handle.contains(key_userimg)
                && handle.contains(key_read)
                && handle.contains(key_Code);
        if (!b) {
            load();
        }
    }

    public LiveData<Integer> getCode() {
        return handle.getLiveData(key_Code);
    }//总积分

    public LiveData<Integer> getId() {
        return handle.getLiveData(key_id);
    }//id

    public LiveData<String> getPhone() {
        return handle.getLiveData(key_phone);
    }//账号

    public LiveData<String> getPassword() {
        return handle.getLiveData(key_password);
    }//密码

    public LiveData<String> getUsername() {
        return handle.getLiveData(key_name);
    }//用户名

    public LiveData<Integer> getGender() {
        return handle.getLiveData(key_gender);
    }//性别

    public LiveData<Integer> getGrade() {
        return handle.getLiveData(key_grade);
    }//积分

    public LiveData<Integer> getIn() {
        return handle.getLiveData(KEY_OUT_IN);
    }//状态

    public LiveData<Integer> getDz() {
        return handle.getLiveData(key_dz);
    }//点赞

    public LiveData<Integer> getDays() {
        return handle.getLiveData(key_study_day);
    }//打卡

    public LiveData<Integer> getTimes() {
        return handle.getLiveData(key_study_time);
    }

    public LiveData<Integer> getPk() {
        return handle.getLiveData(key_pk);
    }

    public LiveData<Integer> getReply() {
        return handle.getLiveData(key_reply);
    }

    public LiveData<Integer> getLevel() {
        return handle.getLiveData(key_level);
    }

    public LiveData<Integer> getDraw() {
        return handle.getLiveData(key_draw);
    }

    public LiveData<Integer> getREAD() {
        return handle.getLiveData(key_read);
    }

    public LiveData<String> getUserImg() {
        return handle.getLiveData(key_userimg);
    }//头像

    public void load() {
        SharedPreferences shp = getApplication().getSharedPreferences("User_Tab", Context.MODE_PRIVATE);

        int code=shp.getInt(key_Code,0);
        handle.set(key_Code,code);

        int num=shp.getInt(key_read,0);
        handle.set(key_read,num);

        String img = shp.getString(key_userimg, null);
        handle.set(key_userimg, img);

        String phone = shp.getString(key_phone, null);
        handle.set(key_phone, phone);

        String password = shp.getString(key_password, null);
        handle.set(key_password, password);

        String username = shp.getString(key_name, null);
        handle.set(key_name, username);

        int grade = shp.getInt(key_grade, 0);
        handle.set(key_grade, grade);

        int gender = shp.getInt(key_gender, 0);
        handle.set(key_gender, gender);

        int in_out = shp.getInt(KEY_OUT_IN, 0);
        handle.set(KEY_OUT_IN, in_out);

        int dz = shp.getInt(key_dz, 0);
        handle.set(key_dz, dz);

        int day = shp.getInt(key_study_day, 0);
        handle.set(key_study_day, day);

        int time = shp.getInt(key_study_time, 0);
        handle.set(key_study_time, time);

        int pk = shp.getInt(key_pk, 0);
        handle.set(key_pk, pk);

        int reply = shp.getInt(key_reply, 0);
        handle.set(key_reply, reply);

        int level = shp.getInt(key_level, 0);
        handle.set(key_level, level);

        int draw = shp.getInt(key_draw, 0);
        handle.set(key_draw, draw);

        int id = shp.getInt(key_id, 0);
        handle.set(key_id, id);

    }

    public void save() {
        SharedPreferences shp = getApplication().getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();

        if (getREAD().getValue() == null) {
            editor.putInt(key_Code, 0);
        } else {
            editor.putInt(key_Code, getCode().getValue());
        }

        if (getREAD().getValue() == null) {
            editor.putInt(key_read, 0);
        } else {
            editor.putInt(key_read, getREAD().getValue());
        }
        //总积分
        if (getGrade().getValue() == null) {
            editor.putInt(key_grade, 0);
        } else {
            editor.putInt(key_grade, getGrade().getValue());
        }
        //状态
        if (getIn().getValue() == null) {
            editor.putInt(KEY_OUT_IN, 0);
        } else {
            editor.putInt(KEY_OUT_IN, getIn().getValue());
        }
        //性别
        if (getGender().getValue() == null) {
            editor.putInt(key_gender, 0);
        } else {
            editor.putInt(key_gender, getIn().getValue());
        }
        //时间
        if (getTimes().getValue() == null) {
            editor.putInt(key_study_time, 0);
        } else {
            editor.putInt(key_study_time, getTimes().getValue());
        }
        //点赞
        if (getDz().getValue() == null) {
            editor.putInt(key_dz, 0);
        } else {
            editor.putInt(key_dz, getDz().getValue());
        }
        //打卡
        if (getDays().getValue() == null) {
            editor.putInt(key_study_day, 0);
        } else {
            editor.putInt(key_study_day, getDays().getValue());
        }
        //pk
        if (getPk().getValue() == null) {
            editor.putInt(key_pk, 0);
        } else {
            editor.putInt(key_pk, getPk().getValue());
        }
        //答题积分
        if (getReply().getValue() == null) {
            editor.putInt(key_reply, 0);
        } else {
            editor.putInt(key_reply, getReply().getValue());
        }
        //拼图等级
        if (getLevel().getValue() == null) {
            editor.putInt(key_level, 0);
        } else {
            editor.putInt(key_level, getLevel().getValue());
        }
        //画画
        if (getDraw().getValue() == null) {
            editor.putInt(key_draw, 0);
        } else {
            editor.putInt(key_draw, getDraw().getValue());
        }
        //id
        editor.putString(key_userimg,getUserImg().getValue());
        editor.putInt(key_id, getId().getValue());
        editor.putString(key_phone, getPhone().getValue());
        editor.putString(key_password, getPassword().getValue());
        editor.putString(key_name, getUsername().getValue());
        editor.apply();
    }
    public void insertAll(User_Form user_form){
        handle.set(key_Code,user_form.getCode());
        handle.set(key_read,user_form.getReadnumber());
        handle.set(key_userimg,user_form.getUserimg());
        handle.set(key_id,user_form.getId());
        handle.set(key_draw,user_form.getDrawright());
        handle.set(key_reply,user_form.getReplycode());
        handle.set(key_level,user_form.getLevel());
        handle.set(key_pk,user_form.getPkcode());
        handle.set(key_grade,user_form.getCode());
        handle.set(key_study_time,user_form.getStudytimecount());
        handle.set(key_dz,user_form.getDzcount());
        handle.set(key_study_day,user_form.getStudydayscount());
        handle.set(key_gender,user_form.getGender());
        handle.set(key_name,user_form.getUsername());
        handle.set(key_password,user_form.getPassword());
        handle.set(key_phone,user_form.getUserphone());
    }

    public void insertCode(int code) {
        handle.set(key_Code, code);
    }

    public void insertId(int id) {
        handle.set(key_id, id);
    }

    public void insertPhone(String phone) {
        handle.set(key_phone, phone);
    }

    public void insertPassword(String password) {
        handle.set(key_password, password);
    }

    public void insertName(String name) {
        handle.set(key_name, name);
    }

    public void insertGrade(int grade) {
        handle.set(key_grade, grade);
    }

    public void insertGender(int gender) {
        handle.set(key_gender, gender);
    }

    public void insertDay(int day) {
        handle.set(key_study_day, day);
    }

    public void insertTime(int time) {
        handle.set(key_study_time, time);
    }

    public void insertDz(int n) {
        handle.set(key_dz, n);
    }

    public void insertPk(int n) {
        handle.set(key_pk, n);
    }

    public void insertLevel(int n) {
        handle.set(key_level, n);
    }

    public void insertReply(int n) {
        handle.set(key_reply, n);
    }

    public void insertDraw(int n) {
        handle.set(key_draw, n);
    }


    public void addPk() {
        handle.set(key_pk, getPk().getValue() == null ? 2 : getPk().getValue() + 2);
    }

    public void addLevel() {
        handle.set(key_level, getLevel().getValue() == null ? 1 : getLevel().getValue() + 1);
    }

    public void addReply() {
        handle.set(key_reply, getReply().getValue() == null ? 1 : getReply().getValue() + 1);
    }

    public void addDraw() {
        handle.set(key_draw, getDraw().getValue() == null ? 1 : getDraw().getValue() + 1);
    }

    public void addDay() {
        handle.set(key_study_day, getDays().getValue() == null ? 1 : getDays().getValue() + 1);
    }

    public void addTime(int n) {
        handle.set(key_study_time, getTimes().getValue() == null ? 1 : getTimes().getValue() + 1);
    }

    public void addDz() {
        handle.set(key_dz, getDz().getValue() == null ? 1 : getDz().getValue() + 1);
    }

    public void update_IN() {
        handle.set(KEY_OUT_IN, 1);
    }

    public void update_OUT() {
        handle.set(KEY_OUT_IN, 0);
    }

    public void clearAll() {
        handle.set(key_phone, null);
        handle.set(key_password, null);
        handle.set(key_name, null);
        handle.set(key_userimg, null);
        handle.set(key_grade, 0);
        handle.set(key_gender, 0);
        handle.set(key_dz, 0);
        handle.set(key_study_day, 0);
        handle.set(key_study_time, 0);
        handle.set(key_draw, 0);
        handle.set(key_level, 0);
        handle.set(key_reply, 0);
        handle.set(key_pk, 0);
        handle.set(key_id, 0);
        handle.set(key_read,0);
        handle.set(key_Code,0);
        save();
    }

}

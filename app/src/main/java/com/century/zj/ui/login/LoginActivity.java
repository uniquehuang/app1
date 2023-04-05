package com.century.zj.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.widget.Toast;


import com.century.zj.api.Api;
import com.century.zj.api.ApiConfig;
import com.century.zj.api.TtitCallback;
import com.century.zj.databinding.ActivityLoginBinding;

import com.century.zj.entity.LoginResponse;
import com.century.zj.entity.User_Form;
import com.century.zj.ui.forget_password.ForgetActivity;


import com.century.zj.ui.register.RegisterActivity;
import com.century.zj.util.UserConfigForm;
import com.century.zj.viewmodel.MyViewModel;
import com.google.gson.Gson;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding activityLoginBinding;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        myViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(
                this.getApplication(),
                this,
                savedInstanceState)).get(MyViewModel.class);
        activityLoginBinding.backOff.setOnClickListener(v -> finish());
        activityLoginBinding.quickRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        activityLoginBinding.forgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
            startActivity(intent);
        });
        activityLoginBinding.button.setOnClickListener(v -> login(activityLoginBinding.editTextTextPersonName.getText().toString(), activityLoginBinding.editTextTextPassword.getText().toString()));
    }

    private void login(String account, String pwd) {
        if (TextUtils.isEmpty(activityLoginBinding.editTextTextPersonName.getText().toString())) {
            Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(activityLoginBinding.editTextTextPassword.getText().toString())) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, Object> m = new HashMap<>();
        m.put(UserConfigForm.UP, account);
        m.put(UserConfigForm.PS, pwd);
        Api.config(ApiConfig.LOGIN, m).postRequest(this, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                //利用Gson 反序列化，得到用户数据
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                User_Form user_form = loginResponse.getData();
                if (loginResponse.getCode() != null) {
                    if (loginResponse.getCode().equals("200")&&activityLoginBinding.editTextTextPassword.getText().toString().equals(user_form.getPassword())) {
                        Log.d("TAG", "run: " + res);
                        myViewModel.insertAll(user_form);
                        myViewModel.update_IN();
                        myViewModel.save();
                        Log.v("!!!!!!!!!!!!!!!", String.valueOf(myViewModel.getIn().getValue()));
                        finish();
                    }else {
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "密码有误", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "无该账号", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
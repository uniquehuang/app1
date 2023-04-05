package com.century.zj.ui.mine.setup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;


import android.os.Bundle;

import com.century.zj.databinding.ActivitySetUpBinding;
import com.century.zj.viewmodel.MyViewModel;

public class SetUpActivity extends AppCompatActivity {
    MyViewModel myViewModel;
    ActivitySetUpBinding activitySetUpBinding;
    String[] sexArray = {"男", "女"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySetUpBinding = ActivitySetUpBinding.inflate(getLayoutInflater());
        setContentView(activitySetUpBinding.getRoot());
        activitySetUpBinding.setUPBack.setOnClickListener(v -> finish());
        myViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(
                this.getApplication(),
                this,
                savedInstanceState)).get(MyViewModel.class);
        activitySetUpBinding.editGender.setText(myViewModel.getGender().getValue() != null ? String.valueOf(myViewModel.getGender().getValue()) : "1");
        activitySetUpBinding.editName.setText(myViewModel.getUsername().getValue());
        activitySetUpBinding.passwordGai.setText(myViewModel.getPassword().getValue());
        activitySetUpBinding.editGender.setOnClickListener(v -> {//性别点击后弹出性别选择框
            AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
            // checkedItem默认的选中 setSingleChoiceItems设置单选按钮组
            builder3.setSingleChoiceItems(sexArray, 1, (dialog, which) -> {// which是被选中的位置
                // showToast(which+"");
                activitySetUpBinding.editGender.setText(sexArray[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            });
            builder3.show();// 让弹出框显示
        });
        activitySetUpBinding.button4.setOnClickListener(v -> {
            myViewModel.insertGender(activitySetUpBinding.editGender.getText().equals("男") ? 1 : 0);
            myViewModel.insertName(activitySetUpBinding.editName.getText().toString());
            myViewModel.insertPassword(activitySetUpBinding.passwordGai.getText().toString());
            myViewModel.save();
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
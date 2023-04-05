package com.century.zj.ui.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.databinding.ActivityMineBinding;
import com.century.zj.ui.login.LoginActivity;

import com.century.zj.ui.mine.collection.CollectionActivity;
import com.century.zj.ui.mine.cricle.CircleActivity;
import com.century.zj.ui.mine.note.NoteActity;
import com.century.zj.ui.mine.setup.SetUpActivity;
import com.century.zj.viewmodel.MyViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class MineActivity extends AppCompatActivity {
    ActivityMineBinding activityMineBinding;
    MyViewModel myViewModel;
    SharedPreferences shp;
    boolean isFresh=false;
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel= new ViewModelProvider(this,new SavedStateViewModelFactory(
                this.getApplication(),
                this,
                savedInstanceState)).get(MyViewModel.class);
        myViewModel.load();
        activityMineBinding = ActivityMineBinding.inflate(getLayoutInflater());
        setContentView(activityMineBinding.getRoot());
        AppBarLayout appBarLayout=findViewById(R.id.appBar);
        Toolbar toolbar = findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {
                    activityMineBinding.nameTitle.setTextColor(R.color.black);
                    toolbar.setBackground(getDrawable(R.color.white2));
                    //展开状态
                }else if(state == State.COLLAPSED){
                    activityMineBinding.nameTitle.setTextColor(R.color.white);
                    toolbar.setBackground(getDrawable(R.color.red_two));
                    //折叠状态
                }else {
                    //中间状态
                }
            }
        });
        activityMineBinding.mineCircle.setOnClickListener(v -> startActivity(new Intent(this, CircleActivity.class)));
        activityMineBinding.mineCollection.setOnClickListener(v -> startActivity(new Intent(this, CollectionActivity.class)));
        activityMineBinding.mineNote.setOnClickListener(v -> startActivity(new Intent(this, NoteActity.class)));
        activityMineBinding.mineAbout.setOnClickListener(v -> Toast.makeText(this,"当前版本:1.0.1",Toast.LENGTH_SHORT).show());
        shp=getApplication().getSharedPreferences("User_Tab", Context.MODE_PRIVATE);

        if (shp.getString("username",null)==null){
            if (shp.getString("userphone",null)==null){
                activityMineBinding.mineName.setText("游客");
            }else {
                activityMineBinding.mineName.setText("用户"+shp.getString("userphone",null));
            }
        }else {
            activityMineBinding.mineName.setText(shp.getString("username",null));
        }
        if (myViewModel.getIn().getValue()!=null){
            if (myViewModel.getIn().getValue()==0||activityMineBinding.mineName.getText().equals("游客")){
                activityMineBinding.mineSetUp.setOnClickListener(v -> Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show());
                activityMineBinding.mineCollection.setOnClickListener(v -> Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show());
                activityMineBinding.mineCircle.setOnClickListener(v -> Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show());
                activityMineBinding.mineNote.setOnClickListener(v -> Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show());
                activityMineBinding.days.setText(String.valueOf(0));
                activityMineBinding.DZ.setText(String.valueOf(0));
                activityMineBinding.login.setText("登录");
            }else {
                activityMineBinding.mineSetUp.setOnClickListener(v -> startActivity(new Intent(MineActivity.this, SetUpActivity.class)));
                Log.v("!!!!!!!!!!!!!!!", String.valueOf(myViewModel.getIn().getValue()));
                activityMineBinding.DZ.setText(String.valueOf(shp.getInt("dzcount",0)));
                activityMineBinding.days.setText(String.valueOf(shp.getInt("studydayscount",0)));
                activityMineBinding.read.setText(String.valueOf(shp.getInt("readnumber",0)));
                activityMineBinding.login.setText("退出");
                if (myViewModel.getUserImg().getValue()!=null){
                    Glide.with(this).load(myViewModel.getUserImg().getValue()).into(activityMineBinding.userHead);

                }
            }
        }
        activityMineBinding.login.setOnClickListener(v -> {
            if (myViewModel.getIn().getValue()==0){
                isFresh=true;
                Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                startActivity(intent);
            }else {
                myViewModel.update_OUT();
                myViewModel.clearAll();
                finish();
            }
        });
         }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFresh){
            isFresh=false;
            startActivity(new Intent(this, MineActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
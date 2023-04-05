package com.century.zj.ui.main.Game.answer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.century.zj.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AnswerGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_game);
        @SuppressLint("SdCardPath") String DB_PATH = "/data/data/com.century.zj/databases/";
        String DB_NAME = "question.db";
        //应用启动时，判断数据库是否存在，不存在则将提前打包好的数据库文件复制到数据库目录下
        //数据库目录不存在时，创建数据库目录
        if (!(new File(DB_PATH + DB_NAME).exists())) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        //定义输入输出流，用于复制文件
        try {
            InputStream is = getBaseContext().getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            //刷新输出流，关闭输入输出流
            os.flush();
            os.close();
            os.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


        ImageButton btn = findViewById(R.id.button1);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(AnswerGame.this, ExamActivity.class);
            startActivity(intent);
        });
    }
}
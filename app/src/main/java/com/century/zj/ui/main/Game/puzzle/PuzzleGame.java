package com.century.zj.ui.main.Game.puzzle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.century.zj.R;
import com.century.zj.http.RetrofitFactory;

import java.util.Random;


public class PuzzleGame extends AppCompatActivity {
    private GameView gameview;
    private TextView tv_level, tv_time, game_text;
    private Button game_next;
    ImageView image_over;
    int j;
    boolean isFinish=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_game);
        image_over=findViewById(R.id.puzzle_over);
        tv_level = findViewById(R.id.tv_level);
        tv_time = findViewById(R.id.tv_time);
        gameview = findViewById(R.id.gameview);
        ImageView imageBack = findViewById(R.id.puzzleGame_back);
        game_text = findViewById(R.id.gameText);
        game_next = findViewById(R.id.gameNext);
        SharedPreferences sharedPreferences=this.getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=sharedPreferences.edit();
        int user_id=sharedPreferences.getInt("id",0);
        int game_level=sharedPreferences.getInt("level",0);
        int s=sharedPreferences.getInt("USER",0);
        imageBack.setOnClickListener(v -> {
            if (game_level<--j&&s==1){
                editor.putInt("level",j);
                editor.apply();
                Observable<Object> observable= RetrofitFactory.getInstence().API().postPuzzleGame(user_id,j);
                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {

                            }
                        });
            }
            finish();
        });
        getBit();
        //显示时间
        gameview.setTimeEnabled(true);
        gameview.setOnGameListener(new GameView.GamePintuListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void nextLevel(final int nextLevel) {
                new AlertDialog.Builder(PuzzleGame.this)
                        .setTitle("拼图完成")
                        .setMessage(game_level<j&&s==1?"达成新纪录":"挑战成功")
                        .setNegativeButton("学习知识", (dialog, which) -> {
                            isFinish=true;
                            tv_level.setVisibility(View.GONE);
                            tv_time.setVisibility(View.GONE);
                            gameview.setVisibility(View.GONE);
                            image_over.setVisibility(View.VISIBLE);
                            game_next.setVisibility(View.VISIBLE);
                            game_text.setVisibility(View.VISIBLE);
                        })
                        .setPositiveButton("下一关", (dialog, which) -> {
                            isFinish=false;
                            getBit();
                            gameview.nextLevel();
                        }).show();
                game_next.setOnClickListener(v -> {
                    tv_level.setVisibility(View.VISIBLE);
                    tv_time.setVisibility(View.VISIBLE);
                    gameview.setVisibility(View.VISIBLE);
                    image_over.setVisibility(View.GONE);
                    game_next.setVisibility(View.GONE);
                    game_text.setVisibility(View.GONE);
                    getBit();
                    gameview.nextLevel();
                });
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void timechanged(int time) {
                //设置时间
                tv_time.setText("倒计时：" + time);
            }

            @Override
            public void gameOver() {
                new AlertDialog.Builder(PuzzleGame.this).setTitle("游戏结束").setMessage("很遗憾挑战失败").setPositiveButton("重新开始", (dialog, which) -> gameview.restartGame()).setNegativeButton("退出", (dialog, which) -> finish()).show();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        gameview.pauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameview.resumeGame();
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void getBit() {
        tv_level.setText("当前关卡:" +(++j));
        int[] picture ={R.drawable.pt1,R.drawable.pt2,R.drawable.pt3,R.drawable.pt4,R.drawable.pt5,R.drawable.pt5,R.drawable.pt7};
        int[] text ={R.string.pt1,R.string.pt2,R.string.pt3,R.string.pt4,R.string.pt5,R.string.pt6,R.string.pt7};
        Random random = new Random();
        int i = random.nextInt(7);
        image_over.setImageBitmap(drawableToBitmap(getDrawable(picture[i])));
        gameview.setmBitmap(drawableToBitmap(getDrawable(picture[i])));
        game_text.setText(getResources().getText(text[i]));
    }

    /**
     * Drawable转换成一个Bitmap
     * 方法一
     *
     * @param drawable drawable对象
     * @return bitmap
     */


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
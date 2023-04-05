package com.century.zj.ui.main.Game.answer;

import androidx.appcompat.app.AlertDialog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.century.zj.R;
import com.century.zj.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends Activity {

    private int count;
    private int current;
    private boolean wrongMode;//标志变量，判断是否进入错题模式

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        DBService dbService = new DBService();
        final List<Question> list = dbService.getQuestion();

        count = list.size();
        current = 0;
        wrongMode = false;//默认情况

        final TextView tv_question = findViewById(R.id.question);
        final RadioButton[] radioButtons = new RadioButton[4];
        radioButtons[0] = findViewById(R.id.answerA);
        radioButtons[1] = findViewById(R.id.answerB);
        radioButtons[2] = findViewById(R.id.answerC);
        radioButtons[3] = findViewById(R.id.answerD);
        Button btn_previous = findViewById(R.id.btn_previous);
        Button btn_next = findViewById(R.id.btn_next);
        TextView tv=findViewById(R.id.question_back);
        tv.setOnClickListener(v-> new AlertDialog.Builder(this).setMessage("是否退出").setNegativeButton("取消",(dialogInterface, i)->{}).setPositiveButton("确认",(dialogInterface, i)->finish()).show());
        final TextView tv_explaination = findViewById(R.id.explaination);
        final ImageView tv_imagebg = findViewById(R.id.imagebg);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        //为控件赋值
        Question q = list.get(0);
        tv_question.setText(q.question);
        tv_explaination.setText(q.explaination);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);

        btn_next.setOnClickListener(view -> {
            if (current < count - 1) {//若当前题目不为最后一题，点击next按钮跳转到下一题；否则不响应
                current++;
                //更新题目
                Question q1 = list.get(current);
                tv_question.setText(q1.question);
                radioButtons[0].setText(q1.answerA);
                radioButtons[1].setText(q1.answerB);
                radioButtons[2].setText(q1.answerC);
                radioButtons[3].setText(q1.answerD);
                tv_explaination.setText(q1.explaination);

                //若之前已经选择过，则应记录选择
                radioGroup.clearCheck();
                if (q1.selectedAnswer != -1) {
                    radioButtons[q1.selectedAnswer].setChecked(true);
                }

            }
            //错题模式的最后一题
            else if (current == count - 1 && wrongMode) {
                new AlertDialog.Builder(ExamActivity.this)
                        .setTitle("提示")
                        .setMessage("已经到达最后一题，是否退出？")
                        .setPositiveButton("确定", (dialogInterface, i) -> ExamActivity.this.finish())
                        .setNegativeButton("取消", null)
                        .show();

            } else {
                //当前题目为最后一题时，告知用户作答正确的数量和作答错误的数量，并询问用户是否要查看错题
                final List<Integer> wrongList = checkAnswer(list);
                //作对所有题目
                if (wrongList.size() == 0) {
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("恭喜你全部回答正确！")
                            .setPositiveButton("确定", (dialogInterface, i) -> ExamActivity.this.finish()).show();

                } else
                    new AlertDialog.Builder(ExamActivity.this)
                            .setTitle("提示")
                            .setMessage("您答对了" + (list.size() - wrongList.size()) +
                                    "道题目；答错了" + wrongList.size() + "道题目。是否查看错题？")
                            .setPositiveButton("确定", (dialogInterface, which) -> {

                                //判断进入错题模式
                                wrongMode = true;
                                List<Question> newList = new ArrayList<>();
                                //将错误题目复制到newList中
                                for (int i = 0; i < wrongList.size(); i++) {
                                    newList.add(list.get(wrongList.get(i)));
                                }
                                //将原来的list清空
                                list.clear();
                                //将错误题目添加到原来的list中
                                list.addAll(newList);
                                current = 0;
                                count = list.size();
                                //更新显示时的内容
                                Question q1 = list.get(current);
                                tv_question.setText(q1.question);
                                radioButtons[0].setText(q1.answerA);
                                radioButtons[1].setText(q1.answerB);
                                radioButtons[2].setText(q1.answerC);
                                radioButtons[3].setText(q1.answerD);
                                tv_explaination.setText(q1.explaination);
                                //显示解析
                                tv_explaination.setVisibility(View.VISIBLE);
                                tv_imagebg.setVisibility(View.VISIBLE);
                            })
                            .setNegativeButton("取消", (dialogInterface, which) -> {
                                //点击取消时，关闭当前activity
                                ExamActivity.this.finish();
                            }).show();
            }
        });
        btn_previous.setOnClickListener(view -> {
            if (current > 0)//若当前题目不为第一题，点击previous按钮跳转到上一题；否则不响应
            {
                current--;
                Question q12 = list.get(current);
                tv_question.setText(q12.question);
                radioButtons[0].setText(q12.answerA);
                radioButtons[1].setText(q12.answerB);
                radioButtons[2].setText(q12.answerC);
                radioButtons[3].setText(q12.answerD);
                tv_explaination.setText(q12.explaination);


                //若之前已经选择过，则应记录选择
                radioGroup.clearCheck();
                if (q12.selectedAnswer != -1) {
                    radioButtons[q12.selectedAnswer].setChecked(true);
                }

            }

        });
        //选择选项时更新选择
        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            for (int i = 0; i < 4; i++) {
                if (radioButtons[i].isChecked()) {
                    list.get(current).selectedAnswer = i;
                    break;
                }
            }

        });
    }

    /*
   判断用户作答是否正确，并将作答错误题目的下标生成list,返回给调用者
    */
    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
            if (i==list.size()-1){
                if (wrongList.size()>=list.size()*0.6){
                    SharedPreferences sharedPreferences=getSharedPreferences("User_Tab",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("replycode",sharedPreferences.getInt("replycode",0)+  1);
                    editor.putInt("code",sharedPreferences.getInt("code",0)+  1);
                    editor.apply();
                    Observable<Object> observable= RetrofitFactory.getInstence().API().postReplyCode(sharedPreferences.getInt("id",0),sharedPreferences.getInt("replycode",0)+  1);
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
            }
        }
        return wrongList;
    }
}
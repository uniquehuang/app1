package com.century.zj.ui.main.Home_page.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.century.zj.R;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.EventEntity;
import com.century.zj.http.RetrofitFactory;
import com.century.zj.ui.main.Study.Event.EventActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<EventEntity> searchList=new ArrayList<>();
    ClearEditTextView et;
    LinearLayout label,ll;
    RecyclerView rv;
    ConstraintLayout labelLayoutView;
    TextView search_back;
    TextView hot1,hot2,hot3,hot4,hot5;
    TextView[] textViews={hot1,hot2,hot3,hot4,hot5};
    SearchAdapter searchAdapter;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textViews[0]=findViewById(R.id.hot1);
        textViews[1]=findViewById(R.id.hot2);
        textViews[2]=findViewById(R.id.hot3);
        textViews[3]=findViewById(R.id.hot4);
        textViews[4]=findViewById(R.id.hot5);
        search_back=findViewById(R.id.main_back);
        et=findViewById(R.id.main_et);
        label=findViewById(R.id.main_labelll);
        ll=findViewById(R.id.main_ll);
        rv=findViewById(R.id.main_rv);
        labelLayoutView=findViewById(R.id.main_label);
        search_back.setOnClickListener(v ->finish());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        //baseAdapter
        searchAdapter = new SearchAdapter(R.layout.activity_search_card, searchList);
        //设置不同数据发生改变
        searchAdapter.setDiffCallback(new SearchAdapter.DiffEventCallback());
        rv.setAdapter(searchAdapter);
        et.setOnTouchListener((v, event) -> {
            if (et.getCompoundDrawables()[2]!= null){
                //当抬起时
                if (event.getAction()== MotionEvent.ACTION_UP){
                    //当点击位置的X坐标大于EditText宽度减去右间距减去清除图标的宽度并且小于EditText宽度减去右间距之间的区域，即清除图标的位置
                    boolean xTouchable= event.getX()> (et.getWidth()- et.getPaddingRight()- et.mClearDrawable.getIntrinsicWidth())
                            &&(event.getX()< (et.getWidth()- et.getPaddingRight()));
                    boolean yTouchable= event.getY()> (et.getHeight()- et.getPaddingBottom()- et.mClearDrawable.getIntrinsicHeight())
                            &&(event.getY()< (et.getHeight()+ et.mClearDrawable.getIntrinsicHeight())/ 2);
                    //清除文本
                    if (xTouchable&& yTouchable){
                        et.setText("");
                        if (searchList.size()!= 0) {
                            searchList.clear();
                            label.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            return false;
        });
        et.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                if ("".equals(et.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }else {
                    ((InputMethodManager)et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchList.clear();
                    initData(et.getText().toString());
                    label.setVisibility(View.GONE);
                }
                return true;
            }
            return false;
        });
        getRed();
    }
    public void getRed(){
        Observable<BaseEntity<List<EventEntity>>> red=RetrofitFactory.getInstence().API().getEventListAll();
        red.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<EventEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<EventEntity>> listBaseEntity) {
                        for (int i=0;i<5;i++){
                            textViews[i].setText(listBaseEntity.getData().get(i).getTitle());
                            int j=i;
                            textViews[i].setOnClickListener(v -> {
                                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("id", String.valueOf(listBaseEntity.getData().get(j).getId()));
                                bundle.putString("title", listBaseEntity.getData().get(j).getTitle());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            });
                        }
                    }
                });
    }

    public void initData(String title){
        Observable<BaseEntity<List<EventEntity>>> observable= RetrofitFactory.getInstence().API().getTitle(title);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<EventEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<EventEntity>> listBaseEntity) {
                        searchList=listBaseEntity.getData();
                        searchAdapter.setDiffNewData(searchList);
                        searchAdapter.notifyDataSetChanged();
                    }
                });

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Finger touch screen event
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // get current focus,Generally it is EditText
            View view = getCurrentFocus();
            if (isShouldHideSoftKeyBoard(view, ev)) {
                hideSoftKeyBoard(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if ((view instanceof EditText)) {
            int[] l = { 0, 0 };
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top +view.getHeight(), right = left
                    + view.getWidth();
            // If click the EditText event ,ignore it
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // if the focus is EditText,ignore it;
        return false;
    }

    private void hideSoftKeyBoard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
package com.century.zj.ui.main.Game.ranking;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


import android.annotation.SuppressLint;
import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.databinding.ActivityRankBinding;
import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.Rank_Form;
import com.century.zj.http.RetrofitFactory;
import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    ActivityRankBinding activityRankBinding;
    List<Rank_Form> list1 = new ArrayList<>();
    RankingAdapter rankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRankBinding = ActivityRankBinding.inflate(getLayoutInflater());
        setContentView(activityRankBinding.getRoot());
        activityRankBinding.gameRankBack.setOnClickListener(v -> finish());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityRankBinding.recycle.setLayoutManager(linearLayoutManager);
        //baseAdapter
        rankingAdapter = new RankingAdapter(R.layout.rank_card, list1);
        //设置不同数据发生改变
        rankingAdapter.setDiffCallback(new RankingAdapter.DiffEventCallback());
        activityRankBinding.recycle.setAdapter(rankingAdapter);
    }

    private void getEventListByPage() {
        Observable<BaseEntity<List<Rank_Form>>> observable = RetrofitFactory.getInstence().API().getRankList();
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseEntity<List<Rank_Form>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onNext(BaseEntity<List<Rank_Form>> listBaseEntity) {
                        List<Rank_Form> list = listBaseEntity.getData();
                        if (listBaseEntity.isSuccess() && list != null && list.size() > 0) {
                            list1 = list;
                            Glide.with(getApplicationContext()).load(list.get(0).getUserimg()).into(activityRankBinding.rankImage);
/*                            if (list.get(0).getGender()==1){
                                activityRankBinding.rankImage.setImageDrawable(getDrawable(R.drawable.user_boy));
                            }else activityRankBinding.rankImage.setImageDrawable(getDrawable(R.drawable.user_girl));*/
                            activityRankBinding.rankName.setText(list.get(0).getUsername());
                            activityRankBinding.rankCode.setText(String.valueOf(list.get(0).getCode()));
                            rankingAdapter.setDiffNewData(list1);
                            rankingAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEventListByPage();
    }

}
package com.century.zj.ui.main.Forum;

import rx.Observable;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.Talk_Form;
import com.century.zj.http.RetrofitFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForumAdapter extends BaseQuickAdapter<Talk_Form, BaseViewHolder> {
    int DZ_flag;
    int[] imageViews={R.id.friend_image,R.id.friend_imag,R.id.friend_ima};
    public ForumAdapter(int layoutResId, @Nullable List<Talk_Form> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Talk_Form talk_form) {
        String[] url={talk_form.getImgurl1(),talk_form.getImgurl2(),talk_form.getImgurl3()};
        baseViewHolder.setText(R.id.user_name,talk_form.getUsername());
        baseViewHolder.setText(R.id.user_art,talk_form.getArticle());
        baseViewHolder.setText(R.id.time_text,talk_form.getPosttime());
        baseViewHolder.setText(R.id.forumDZ,String.valueOf(talk_form.getDzcount()));
        Glide.with(getContext())
                .load(talk_form.getUserimg())
                .disallowHardwareConfig()
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) baseViewHolder.getView(R.id.image_head));
        if (talk_form.getDzflag()==0){
            baseViewHolder.setImageResource(R.id.ForumImgDZ,R.mipmap.dianzan);
        }else {
            baseViewHolder.setImageResource(R.id.ForumImgDZ,R.mipmap.dianzan_select);
        }
        baseViewHolder.getView(R.id.ForumImgDZ).setOnClickListener(v -> {
            DZ_flag=talk_form.getDzflag();
            if (DZ_flag==0){
                updateUserCount(talk_form.getUserphone(),1);
                updateCount(baseViewHolder,talk_form,1,1);
            }else {
                updateUserCount(talk_form.getUserphone(),-1);
                updateCount(baseViewHolder,talk_form,-1,0);
            }
        });
        if (talk_form.getImgurl1()!=null||talk_form.getImgurl2()!=null||talk_form.getImgurl3()!=null){
            baseViewHolder.getView(R.id.linear2).setVisibility(View.VISIBLE);
            for (int i=0;i<3;i++){
                loadUrl(baseViewHolder,url[i],imageViews[i]);
            }
        }else {
            baseViewHolder.getView(R.id.linear2).setVisibility(View.GONE);
        }
    }

    public static class DiffEventCallback extends DiffUtil.ItemCallback<Talk_Form>{

        @Override
        public boolean areItemsTheSame(@NonNull Talk_Form oldItem, @NonNull Talk_Form newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Talk_Form oldItem, @NonNull Talk_Form newItem) {
            return false;
        }
    }

    public void update(int id,int flag,String root){
        Observable<Object> observable= RetrofitFactory.getInstence().API().DZ(flag,id,root);
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

    public void updateCount(@NonNull BaseViewHolder baseViewHolder,Talk_Form talk_form,int dz,int flag){
        talk_form.setDzflag(flag);
        talk_form.setDzcount(talk_form.getDzcount()+dz);
        if (flag==1){
            baseViewHolder.setImageResource(R.id.ForumImgDZ,R.mipmap.dianzan_select);
        }else {
            baseViewHolder.setImageResource(R.id.ForumImgDZ,R.mipmap.dianzan);
        }
        baseViewHolder.setText(R.id.forumDZ,String.valueOf(talk_form.getDzcount()));
        update(talk_form.getId(),flag,talk_form.getUserphone());
    }

    public void updateUserCount(String root,int dz){
        SharedPreferences shp = getContext().getSharedPreferences("User_Tab", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        if (root.equals(shp.getString("userphone",null))){
            editor.putInt("dzcount",shp.getInt("dzcount",0)+dz);
            editor.apply();
            Observable<Object> observable= RetrofitFactory.getInstence().API().postDZ(shp.getInt("dzcount",0),root);
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

    public void loadUrl(@NonNull BaseViewHolder baseViewHolder,String URL, int view){
        Glide.with(getContext())
                .load(URL)
                .disallowHardwareConfig()
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) baseViewHolder.getView(view));
    }
}

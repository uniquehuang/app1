package com.century.zj.ui.main.Game.ranking;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.century.zj.R;
import com.century.zj.entity.Rank_Form;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class RankingAdapter extends BaseQuickAdapter<Rank_Form, BaseViewHolder> {
    int i=0;
    public RankingAdapter(int layoutResId, @Nullable List<Rank_Form> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Rank_Form rank_form) {
        baseViewHolder.setText(R.id.NO,String.valueOf(++i));
        baseViewHolder.setText(R.id.user_name,rank_form.getUsername());
        baseViewHolder.setText(R.id.user_code,String.valueOf(rank_form.getCode()));
        Glide.with(getContext())
                .load(rank_form.getUserimg())
                .disallowHardwareConfig()
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(8)
                        ))
                .into((ImageView) baseViewHolder.getView(R.id.image_head));
        if (rank_form.getGender()==1){
            baseViewHolder.setImageResource(R.id.image_head, R.drawable.user_boy);
        }else baseViewHolder.setImageResource(R.id.image_head, R.drawable.user_girl);
    }

    public static class DiffEventCallback extends DiffUtil.ItemCallback<Rank_Form>{

        @Override
        public boolean areItemsTheSame(@NonNull Rank_Form oldItem, @NonNull Rank_Form newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rank_Form oldItem, @NonNull Rank_Form newItem) {
            return false;
        }
    }
}

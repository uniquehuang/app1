package com.century.zj.ui.main.Study.Literature;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.entity.EventEntity;
import com.century.zj.entity.LiteratureEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class LiteratureBaseAdapter extends BaseQuickAdapter<LiteratureEntity, BaseViewHolder> {

    public LiteratureBaseAdapter(int layoutResId, @Nullable List<LiteratureEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, LiteratureEntity literatureEntity) {
        baseViewHolder.setText(R.id.item_title,literatureEntity.getTitle());
        baseViewHolder.setText(R.id.item_author,literatureEntity.getAuthor());
        Glide.with(getContext())
                .load(literatureEntity.getImgurl())
                .into((ImageView) baseViewHolder.getView(R.id.item_iv_cardViewBak));


    }

    public static class DiffLiteratureCallback extends DiffUtil.ItemCallback<LiteratureEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull LiteratureEntity oldItem, @NonNull LiteratureEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull LiteratureEntity oldItem, @NonNull LiteratureEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getAuthor().equals(newItem.getAuthor())
                    && oldItem.getImgurl().equals(newItem.getImgurl());
        }
    }
}

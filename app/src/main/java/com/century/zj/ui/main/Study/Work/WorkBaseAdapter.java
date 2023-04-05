package com.century.zj.ui.main.Study.Work;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.entity.WorkEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class WorkBaseAdapter extends BaseQuickAdapter<WorkEntity, BaseViewHolder> {


    public WorkBaseAdapter(int layoutResId, @Nullable List<WorkEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, WorkEntity workEntity) {
        baseViewHolder.setText(R.id.item_title,workEntity.getTitle());
        baseViewHolder.setText(R.id.item_note,workEntity.getAuthor());
        baseViewHolder.setText(R.id.item_category,workEntity.getCategory());
        Glide.with(getContext())
                .load(workEntity.getImgurl())
                .into((ImageView) baseViewHolder.getView(R.id.item_iv_cardViewBak));

    }
    public static class DiffWorkCallback extends DiffUtil.ItemCallback<WorkEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull WorkEntity oldItem, @NonNull WorkEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull WorkEntity oldItem, @NonNull WorkEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getAuthor().equals(newItem.getAuthor())
                    && oldItem.getImgurl().equals(newItem.getImgurl())
                    && oldItem.getCategory().equals(newItem.getCategory());
        }
    }
}

package com.century.zj.ui.main.Study.Person;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.century.zj.R;
import com.century.zj.entity.LiteratureEntity;
import com.century.zj.entity.PersonEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class PersonBaseAdapter extends BaseQuickAdapter<PersonEntity, BaseViewHolder> {

    public PersonBaseAdapter(int layoutResId, @Nullable List<PersonEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PersonEntity personEntity) {
        baseViewHolder.setText(R.id.item_title,personEntity.getTitle());
        baseViewHolder.setText(R.id.item_age,personEntity.getAge());
        baseViewHolder.setText(R.id.item_text,personEntity.getText());
        Glide.with(getContext())
                .load(personEntity.getImgurl())
                .into((ImageView) baseViewHolder.getView(R.id.item_iv_cardViewBak));

    }
    public static class DiffPersonCallback extends DiffUtil.ItemCallback<PersonEntity>{

        @Override
        public boolean areItemsTheSame(@NonNull PersonEntity oldItem, @NonNull PersonEntity newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PersonEntity oldItem, @NonNull PersonEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getAge().equals(newItem.getAge())
                    && oldItem.getImgurl().equals(newItem.getImgurl());
        }
    }
}

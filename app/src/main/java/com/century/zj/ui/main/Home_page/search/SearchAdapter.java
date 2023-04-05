package com.century.zj.ui.main.Home_page.search;

import android.content.Intent;
import android.os.Bundle;

import com.century.zj.R;
import com.century.zj.entity.EventEntity;
import com.century.zj.ui.main.Study.Event.EventActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class SearchAdapter extends BaseQuickAdapter<EventEntity, BaseViewHolder> {

    public SearchAdapter(int layoutResId, @Nullable List<EventEntity> data) {
        super(layoutResId, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, EventEntity item) {
        helper.setText(R.id.search_id, item.getTitle());
        helper.getView(R.id.search_tab).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EventActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(item.getId()));
            bundle.putString("title",item.getTitle());
            intent.putExtras(bundle);
            SearchActivity searchActivity=(SearchActivity)getContext();
            getContext().startActivity(intent);
            searchActivity.finish();
        });
    }


    public static class DiffEventCallback extends DiffUtil.ItemCallback<EventEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return false;
        }
    }
}

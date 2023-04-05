package com.century.zj.ui.main.Home_page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private int[] imageResIDs;
    Context context;

    public ImageAdapter(int[] imageResIDs, Context context) {
        this.imageResIDs = imageResIDs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//用于循环滚动
    }

    @Override
    public Object getItem(int position) {
        if (position >= imageResIDs.length) {
            position = position % imageResIDs.length;
        }

        return position;
    }

    @Override
    public long getItemId(int position) {
        if (position >= imageResIDs.length) {
            position = position % imageResIDs.length;
        }

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView != null) {
            imageView = (ImageView) convertView;
        } else {
            imageView = new ImageView(context);
        }

        if (position >= imageResIDs.length) {
            position = position % imageResIDs.length;
        }

        Bitmap bitmap = ImageUtil.getImageBitmap(context.getResources(),
                imageResIDs[position]);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        drawable.setAntiAlias(true); // 消除锯齿
        imageView.setImageDrawable(drawable);
        Gallery.LayoutParams params = new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        return imageView;
    }
}

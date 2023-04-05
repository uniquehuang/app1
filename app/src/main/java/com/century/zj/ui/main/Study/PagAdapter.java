package com.century.zj.ui.main.Study;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
     * @type 使用viewpager，不能实现懒加载
     * @explain
     * @author admin.
     * @creat time  22:26.
     */


public class PagAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;
    List<String> titles;


    public PagAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list, List<String> titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

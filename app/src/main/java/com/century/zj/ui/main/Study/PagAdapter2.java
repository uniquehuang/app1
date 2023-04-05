package com.century.zj.ui.main.Study;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


/**
     * @type  使用viewpager2，可以实现懒加载
     * @explain
     * @author admin.
     * @creat time  22:27.
     */


public class PagAdapter2 extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments;
    public PagAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments=fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}

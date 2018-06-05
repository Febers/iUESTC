package com.febers.iuestc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.febers.iuestc.module.news.view.NewsFragmentManager;

public class AdapterNewsViewPager extends FragmentPagerAdapter {

    private static final String TAG = "AdapterNewsViewPager";
    private String[] titles;
    private int type;

    public AdapterNewsViewPager(FragmentManager fragmentManager, String[] titles, int type) {
        super(fragmentManager);
        this.titles = titles;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragmentManager.getInstance(type, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }


}

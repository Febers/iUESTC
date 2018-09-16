/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.febers.iuestc.view.manager.NewsFragmentManager;

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

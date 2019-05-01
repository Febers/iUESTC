/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:02
 *
 */

package com.febers.iuestc.view.adapter;

import com.febers.iuestc.view.manager.GradeFragmentManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterGradeViewPager extends FragmentPagerAdapter {

    private static final String TAG = "AdapterGradeViewPager";
    private String[] titles = new String[] {"列表", "趋势"};

    public AdapterGradeViewPager(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return GradeFragmentManager.getInstance(0);
        } else if (position == 1) {
            return GradeFragmentManager.getInstance(1);
        }
        return GradeFragmentManager.getInstance(0);
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

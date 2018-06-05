package com.febers.iuestc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.febers.iuestc.module.grade.view.GradeFragmentManager;

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

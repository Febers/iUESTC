/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-3 下午11:07
 *
 */

package com.febers.iuestc.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.entity.EventTheme;
import com.febers.iuestc.module.service.view.ServiceActivity;
import com.febers.iuestc.util.SPUtil;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import me.yokeyword.fragmentation.ISupportFragment;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener
        , NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private Boolean mThemeChange = false;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private BottomNavigationBar mBottomNavigationBar;
    private List<ISupportFragment> mFragmentList = new ArrayList<>();

    @Override
    protected int setView() {
        return R.layout.activity_home;
    }

    @Override
    protected Boolean registerEventBus() {
        return true;
    }

    @Override
    protected void findViewById() {
        mDrawerLayout = findViewById(R.id.dl_home);
        mNavigationView = findViewById(R.id.nv_home);
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
    }

    @Override
    protected void initView() {
        int openPosition = 0;
        if (!SPUtil.getInstance().get("is_login", false)) {
            openPosition = 2;
        }

        ISupportFragment firstFragment = findFragment(HomeFirstContainer.class);
        if (firstFragment == null) {
            mFragmentList.add(0, new HomeFirstContainer());
            mFragmentList.add(1, new HomeSecondContainer());
            mFragmentList.add(2, new HomeThirdContainer());
            loadMultipleRootFragment(R.id.home_fragment_layout, openPosition,
                    mFragmentList.get(0), mFragmentList.get(1), mFragmentList.get(2));
        } else {
            mFragmentList.add(0, firstFragment);
            mFragmentList.add(1, findFragment(HomeSecondContainer.class));
            mFragmentList.add(2, findFragment(HomeThirdContainer.class));
        }
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setAutoHideEnabled(true);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_course_bottom_gray,
                        getString(R.string.home_course_table)))
                .addItem(new BottomNavigationItem(R.drawable.ic_all_pink,
                        getString(R.string.home_library)))
                .addItem(new BottomNavigationItem(R.drawable.ic_more_bottom_gray,
                        getString(R.string.home_more)))
                .setFirstSelectedPosition(openPosition)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(HomeActivity.this, ServiceActivity.class);
        switch (item.getItemId()) {
            case R.id.item_nav_query_classroom:
                intent.putExtra("position", 0);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_today_class:
                intent.putExtra("position", 1);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_all_class:
                intent.putExtra("position", 2);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_teacher:
                intent.putExtra("position", 3);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_lxfs:
                intent.putExtra("position", 9);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_exit:
                finish();
            default:
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        showHideFragment(mFragmentList.get(position));
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("theme_change", mThemeChange);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mThemeChange = savedInstanceState.getBoolean("theme_change", false);
            if (mThemeChange) {
                mBottomNavigationBar.selectTab(2);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void themeChange(EventTheme eventTheme) {
        if (eventTheme.getThemeChanged()) {
            mThemeChange = true;
            recreate();
        }
    }
}

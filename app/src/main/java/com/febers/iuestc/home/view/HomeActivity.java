/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午12:27
 *
 */

package com.febers.iuestc.home.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.module.more.ThemeChangeListener;
import com.febers.iuestc.module.service.view.ServiceActivity;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.view.manager.HomeFragmentManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener
        , NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";

    private BottomNavigationBar mBottomNavigationBar;
    private Fragment mLibraryFragment;
    private Fragment mUserFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private Fragment mCourseFragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected int setView() {
        return R.layout.activity_home;
    }

    @Override
    protected void findViewById() {
        mDrawerLayout = findViewById(R.id.dl_home);
        mNavigationView = findViewById(R.id.nv_home);
    }

    @Override
    protected void initView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        int openPosition = 0;
        if (!CustomSharedPreferences.getInstance().get("is_login", false)) {
            openPosition = 2;
        }
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
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

        mLibraryFragment  = HomeFragmentManager.getInstance(1);
        mUserFragment  = HomeFragmentManager.getInstance(2);
        mCourseFragment = HomeFragmentManager.getInstance(0);

        android.support.v4.app.FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_layout, mLibraryFragment);
        mFragmentTransaction.add(R.id.fragment_layout, mUserFragment);
        mFragmentTransaction.add(R.id.fragment_layout, mCourseFragment);
        mFragmentTransaction.commit();

        mFragmentList.add(mCourseFragment);
        mFragmentList.add(mLibraryFragment);
        mFragmentList.add(mUserFragment);
        showFragment(openPosition);
    }

    /**
     * BottomNavigationBar的点击监听
     * @param position 点击的位置
     */
    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                showFragment(0);
                break;
            case 1:
                //图书馆
                showFragment(1);
                break;
            case 2:
                //我的
                showFragment(2);
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void showFragment(int position) {
        android.support.v4.app.FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        for (Fragment f : mFragmentList) {
            mFragmentTransaction.hide(f);
        }
        mFragmentTransaction.show(mFragmentList.get(position));
        /**
         * 不使用commit, 而是commitAllowingStateLoss()，
         * 防止状态恢复时出现 Can not perform this action after onSaveInstanceState 异常
         * 或者重写onSaveInstanceState(Bundle outState)方法，注释掉下面一行
         * super.onSaveInstanceState(outState);
         */
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean fromLib = intent.getBooleanExtra("lib_activity", false);
        Boolean fromTheme = intent.getBooleanExtra("theme_activity", false);
        intent.removeExtra("lib_activity");

        //如果是从lib_activity跳转过来，显示libraryFragment
        if (fromLib) {
            showFragment(1);
            mBottomNavigationBar.selectTab(1);
            return;
        }

        if (fromTheme) {
            showFragment(2);
            mBottomNavigationBar.selectTab(2);
            return;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(Gravity.END)) {
            mDrawerLayout.closeDrawer(Gravity.END);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    public void dateRequest(Boolean isRefresh) {
    }

    @Override
    protected Boolean isSlideBack() {
        return false;
    }
}

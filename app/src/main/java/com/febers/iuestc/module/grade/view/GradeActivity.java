/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-7 下午4:53
 *
 */

package com.febers.iuestc.module.grade.view;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterGradeViewPager;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.view.manager.GradeFragmentManager;

public class GradeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "GradeActivity";
    private Toolbar toolbar;
    private TabLayout tlGrade;
    private ViewPager vpGrade;
    private AdapterGradeViewPager adapterGradeViewPager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected int setView() {
        return R.layout.activity_grade;
    }

    @Override
    protected void findViewById() {
        toolbar = findViewById(R.id.tb_grade);
        vpGrade = findViewById(R.id.vp_grade);
        tlGrade = findViewById(R.id.tl_grade);
        navigationView = findViewById(R.id.nv_grade);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("我的成绩");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        adapterGradeViewPager = new AdapterGradeViewPager(getSupportFragmentManager());
        vpGrade.setAdapter(adapterGradeViewPager);
        tlGrade.setupWithViewPager(vpGrade);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.item_grade_all);
        drawerLayout = findViewById(R.id.dl_grade);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        GradeListFragment listFragment = (GradeListFragment) GradeFragmentManager.getInstance(0);
        switch (item.getItemId()) {
            case R.id.item_grade_13140:
                listFragment.showGradeByTime("2013-2014 1");
                break;
            case R.id.item_grade_13141:
                listFragment.showGradeByTime("2013-2014 2");
                break;
            case R.id.item_grade_14150:
                listFragment.showGradeByTime("2014-2015 1");
                break;
            case R.id.item_grade_14151:
                listFragment.showGradeByTime("2014-2015 2");
                break;
            case R.id.item_grade_15160:
                listFragment.showGradeByTime("2015-2016 1");
                break;
            case R.id.item_grade_15161:
                listFragment.showGradeByTime("2015-2016 2");
                break;
            case R.id.item_grade_16170:
                listFragment.showGradeByTime("2016-2017 1");
                break;
            case R.id.item_grade_16171:
                listFragment.showGradeByTime("2016-2017 2");
                break;
            case R.id.item_grade_17180:
                listFragment.showGradeByTime("2017-2018 1");
                break;
            case R.id.item_grade_17181:
                listFragment.showGradeByTime("2017-2018 2");
                break;
            case R.id.item_grade_all:
                listFragment.showGradeByTime("ic_all_pink");
                break;
            case R.id.item_grade_exit:
                finish();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(Gravity.END);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grade_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_grade_refresh:
                navigationView.setCheckedItem(R.id.item_grade_all);
                GradeListFragment listFragment = (GradeListFragment) GradeFragmentManager.getInstance(0);
                listFragment.dateRequest(true);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

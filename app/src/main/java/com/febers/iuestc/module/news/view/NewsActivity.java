/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-7 下午4:53
 *
 */

package com.febers.iuestc.module.news.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterNewsViewPager;
import com.febers.iuestc.base._BaseActivity;
import com.febers.iuestc.view.custom.CustomViewPager;
import com.febers.iuestc.view.manager.NewsFragmentManager;

public class NewsActivity extends _BaseActivity {

    private static final String TAG = "NewsActivity";
    private int type = 0;
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private AdapterNewsViewPager adapterNewsViewPager;

    @Override
    protected int setView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        if (type == 0) {
            initUnderUI();
            return;
        }
        if (type == 1) {
            initPostUI();
            return;
        }
    }


    //本科生
    private void initUnderUI() {
        Toolbar toolbar = findViewById(R.id.tb_news);
        toolbar.setTitle("本科教务通知");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.vp_news);
        viewPager.setOffscreenPageLimit(3);//非常重要
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = findViewById(R.id.tl_news);
        adapterNewsViewPager = new AdapterNewsViewPager(getSupportFragmentManager(), new String[]{"重要公告", "教学新闻", "实践教学"}, 0);
        viewPager.setAdapter(adapterNewsViewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    //研究生
    private void initPostUI() {
        Toolbar toolbar = findViewById(R.id.tb_news);
        toolbar.setTitle("本科教务通知");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.vp_news);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = findViewById(R.id.tl_news);
        tabLayout.setupWithViewPager(viewPager);

        adapterNewsViewPager = new AdapterNewsViewPager(getSupportFragmentManager(), new String[]{"通知公告", "思想教育", "学生管理"}, 1);
        viewPager.setAdapter(adapterNewsViewPager);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
    }

    /**
     * 在结束activity的时候调用FragmentManager的清除所有Fragment的方法
     * 重置所有的fragment
     * 如果不这样做，FragmentManager不被系统回收，会影响其中type的值
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NewsFragmentManager.clearAllFragment();
    }
}

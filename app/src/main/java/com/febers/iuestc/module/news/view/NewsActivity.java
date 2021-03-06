package com.febers.iuestc.module.news.view;

import android.content.Intent;

import com.febers.iuestc.R;
import com.febers.iuestc.view.adapter.AdapterNewsViewPager;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.view.custom.CustomViewPager;
import com.febers.iuestc.view.manager.NewsFragmentManager;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class NewsActivity extends BaseSwipeActivity {

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

    @Override
    protected int setToolbar() {
        return R.id.tb_news;
    }

    @Override
    protected String setToolbarTitle() {
        if (type == 0) {
            return "本科教务通知";
        }
        if (type == 1) {
            return "研究生教务通知";
        }
        return "教务通知";
    }

    //本科生
    private void initUnderUI() {
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
        viewPager = findViewById(R.id.vp_news);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = findViewById(R.id.tl_news);
        tabLayout.setupWithViewPager(viewPager);

        adapterNewsViewPager = new AdapterNewsViewPager(getSupportFragmentManager(), new String[]{"通知公告", "思想教育", "学生管理"}, 1);
        viewPager.setAdapter(adapterNewsViewPager);
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

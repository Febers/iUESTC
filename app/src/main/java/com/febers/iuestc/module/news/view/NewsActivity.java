package com.febers.iuestc.module.news.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterNewsViewPager;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

public class NewsActivity extends AppCompatActivity {

    private static final String TAG = "NewsActivity";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AdapterNewsViewPager adapterNewsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        SlidrConfig config = new SlidrConfig.Builder()
                .edge(true)
                .build();
        Slidr.attach(this, config);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        Log.d(TAG, "onCreate: type is " + type);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

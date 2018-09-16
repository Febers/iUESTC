/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 下午8:25
 *
 */

package com.febers.iuestc.module.news.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseSwipeActivity;

public class NoticeActivity extends BaseSwipeActivity {

    @Override
    protected int setView() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_notice);
        toolbar.setTitle("通知公告");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = new Intent(NoticeActivity.this, NoticeDetailActivity.class);
        LinearLayout linearLayout1 = findViewById(R.id.ll_notice_jxgl);
        linearLayout1.setOnClickListener((v) -> {
            intent.putExtra("url", "http://wx.jwc.uestc.edu.cn/wx/SchAbout!findNewsInfo.action?partId=37");
            intent.putExtra("title", "教学管理");
            startActivity(intent);
        });
        LinearLayout linearLayout2 = findViewById(R.id.ll_notice_jyjg);
        linearLayout2.setOnClickListener((v) -> {
            intent.putExtra("url", "http://wx.jwc.uestc.edu.cn/wx/SchAbout!findNewsInfo.action?partId=39");
            intent.putExtra("title", "教研教改");
            startActivity(intent);
        });
        LinearLayout linearLayout3 = findViewById(R.id.ll_notice_sjjl);
        linearLayout3.setOnClickListener((v) -> {
            intent.putExtra("url", "http://wx.jwc.uestc.edu.cn/wx/SchAbout!findNewsInfo.action?partId=38");
            intent.putExtra("title", "实践交流");
            startActivity(intent);
        });
        LinearLayout linearLayout4 = findViewById(R.id.ll_notice_jxxw);
        linearLayout4.setOnClickListener((v) -> {
            intent.putExtra("url", "http://wx.jwc.uestc.edu.cn/wx/SchAbout!findNewsInfo.action?partId=40");
            intent.putExtra("title", "教学新闻");
            startActivity(intent);
        });
    }
}

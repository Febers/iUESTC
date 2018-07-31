/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 上午11:29
 *
 */

package com.febers.iuestc.module.more;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.lucasurbas.listitemview.ListItemView;

public class AboutActivity extends BaseActivity implements ListItemView.OnClickListener {

    private static final String TAG = "AboutActivity";
    private ListItemView itemViewWebHome, itemViewVersion, itemViewDeveloper, itemViewEmail;

    @Override
    protected int setView() {
        return R.layout.activity_custom_about;
    }

    @Override
    protected void findViewById() {
        itemViewWebHome = findViewById(R.id.item_view_web_home);
        itemViewDeveloper = findViewById(R.id.item_view_developer);
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_about);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        itemViewWebHome.setOnClickListener(this);
        itemViewDeveloper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_view_web_home:
                Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_web)));
                startActivity(Intent.createChooser(i1, "访问App主页"));
                break;
            case R.id.item_view_developer:
                Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.developer_github)));
                startActivity(Intent.createChooser(i2, "访问开发者Github主页"));
                break;
            default:
                break;
        }
    }
}

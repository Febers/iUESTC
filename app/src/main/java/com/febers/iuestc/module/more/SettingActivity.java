/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午8:09
 *
 */

package com.febers.iuestc.module.more;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.lucasurbas.listitemview.ListItemView;
import com.tencent.bugly.beta.Beta;

public class SettingActivity extends BaseActivity implements ListItemView.OnClickListener {

    private static final String TAG = "SettingActivity";

    private ListItemView itemViewTheme, itemViewUpdate, itemViewAbout;
    @Override
    protected int setView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_setting);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        itemViewTheme = findViewById(R.id.item_view_theme);
        itemViewUpdate = findViewById(R.id.item_view_update);
        itemViewAbout = findViewById(R.id.item_view_about);
        itemViewTheme.setOnClickListener(this);
        itemViewUpdate.setOnClickListener(this);
        itemViewAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_view_theme:
                break;

            case R.id.item_view_update:
                Beta.checkUpgrade();
                Log.i(TAG, "onClick: ");
                break;
            case R.id.item_view_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
            default:
                break;
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-2 上午1:58
 *
 */

package com.febers.iuestc.module.more;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterTheme;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BeanTheme;
import com.febers.iuestc.home.view.HomeActivity;
import com.febers.iuestc.util.CustomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ThemeActivity extends BaseActivity implements ThemeChangeListener {

    @Override
    protected int setView() {
        return R.layout.activity_theme;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_theme);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rvTheme = findViewById(R.id.rv_theme);
        rvTheme.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvTheme.setLayoutManager(new LinearLayoutManager(this));
        AdapterTheme adapterTheme = new AdapterTheme(this, initThemeDate());
        rvTheme.setAdapter(adapterTheme);
    }

    private List<BeanTheme> initThemeDate() {
        List<BeanTheme> themeList = new ArrayList<>();
        BeanTheme Blue = new BeanTheme(R.color.blue, "蓝色");
        BeanTheme night = new BeanTheme(R.color.black, "夜间(实验性)");
        BeanTheme Green = new BeanTheme(R.color.green, "绿色");
        BeanTheme Red = new BeanTheme(R.color.red, "红色");
        BeanTheme purple = new BeanTheme(R.color.purple, "紫色");
        BeanTheme orange = new BeanTheme(R.color.orange, "橙色");
        BeanTheme grey = new BeanTheme(R.color.grey, "灰色");
        BeanTheme teal = new BeanTheme(R.color.teal, "鸭绿");
        BeanTheme pink = new BeanTheme(R.color.pink, "粉色");
        themeList.add(Blue);
        themeList.add(night);
        themeList.add(Green);
        themeList.add(Red);
        themeList.add(purple);
        themeList.add(orange);
        themeList.add(grey);
        themeList.add(teal);
        themeList.add(pink);
        int themeCode = CustomSharedPreferences.getInstance().get("theme_code", 0);
        themeList.get(themeCode).setUsing(true);
        return themeList;
    }

    @Override
    public void onThemeChange(int code) {
        CustomSharedPreferences.getInstance().put("theme_code", code);
        this.recreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ThemeActivity.this, HomeActivity.class);
                intent.putExtra("theme_activity", true);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ThemeActivity.this, HomeActivity.class);
            intent.putExtra("theme_activity", true);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected Boolean isSlideBack() {
        return false;
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
    }
}

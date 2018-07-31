/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.febers.iuestc.R;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.view.custom.CustomProgressDialog;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;


public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    protected CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        choiceTheme();
        setContentView(getContentView());
        if (isSlideBack()) {
            SlidrConfig config = new SlidrConfig.Builder()
                    .edge(true)
                    .build();
            Slidr.attach(this, config);
        }
        findViewById();
        initView();
    }

    protected abstract int setView();
    protected void findViewById() {}
    protected abstract void initView();

    protected int getContentView() {
        return setView();
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(this);
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            runOnUiThread( () -> mProgressDialog.dismiss());
        }
    }

    @Override
    public void onError(String error) {
        runOnUiThread( () -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }

    protected Boolean isSlideBack() {
        return true;
    }

    private void choiceTheme() {
        int themeCode = CustomSharedPreferences.getInstance().get("theme_code", 0);
        if (themeCode == 0) {
            setTheme(R.style.BlueTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
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
}

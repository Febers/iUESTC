/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-3 下午11:02
 *
 */

package com.febers.iuestc.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.febers.iuestc.R;
import com.febers.iuestc.util.CustomSPUtil;
import com.febers.iuestc.util.ThemeUtil;
import com.febers.iuestc.util.ToastUtil;
import com.febers.iuestc.view.custom.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends MySupportActivity implements BaseView {

    protected CustomProgressDialog mProgressDialog;

    protected abstract int setView();

    protected void findViewById(){}

    protected int setMenu() {
        return R.menu.default_menu;
    }

    protected Boolean registerEventBus() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseTheme();
        setContentView(setView());
        findViewById();
        initView();
        if (registerEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    protected abstract void initView();

    private void chooseTheme() {
        int themeCode = CustomSPUtil.getInstance().get("theme_code", 9);
        setTheme(ThemeUtil.getTheme(themeCode));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(setMenu(), menu);
        return true;
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Boolean isLogin = false;
        try {
            isLogin = data.getBooleanExtra("status", false);
        } catch (Exception e) {
        }
        if (isLogin) {
            dateRequest(true);
        }
    }

    @Override
    public void onError(String error) {
        runOnUiThread(()-> {
            ToastUtil.showShortToast(error);
        });
    }
}

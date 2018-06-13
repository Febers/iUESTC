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
import android.widget.Toast;
import com.febers.iuestc.view.CustomProgressDialog;


public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    protected CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
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

}

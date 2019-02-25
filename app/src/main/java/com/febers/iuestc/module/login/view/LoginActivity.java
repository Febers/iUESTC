/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-28 下午5:55
 *
 */

package com.febers.iuestc.module.login.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.entity.BeanLoginStatus;
import com.febers.iuestc.util.CustomSPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.ISupportFragment;

public class LoginActivity extends BaseSwipeActivity {

    private static final String TAG = "LoginActivity";
    private List<ISupportFragment> mFragmentList = new ArrayList<>();
    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @Override
    protected Boolean registerEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mFragmentList.add(0, new LoginViewCustom());
        mFragmentList.add(1, new LoginViewWeb());
        loadMultipleRootFragment(R.id.container_activity_login, 0,
                mFragmentList.get(0), mFragmentList.get(1));
    }

    /**
     * 接收来自Fragment的消息
     *
     * @param event 有两个参数，第一个判断是否登录成功，第二个判断是否需要跳转至webView登录
     * 如果登录成功，返回结果，否则，如果需要跳转到webView登录，则切换Fragment的显示
     * 如果不需要，返回结果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginStatusChange(BaseEvent<BeanLoginStatus> event) {
        Log.i(TAG, "onLoginStatusChange: Data: " + event.getDate()+" Code: " + event.getCode());
        if (event.getCode() == BaseCode.UPDATE) {
            sendLoginResult(true);
            return;
        }
        if (event.getDate().getToWebView()) {
            showHideFragment(mFragmentList.get(1));
        } else {
            sendLoginResult(false);
        }
    }

    private void sendLoginResult(Boolean isSuccess) {
        Intent intent = new Intent();
        if (isSuccess) {
            CustomSPUtil.getInstance().put(getString(R.string.sp_is_login), true);
            intent.putExtra("status", true);
        } else {
            intent.putExtra("status", false);
        }
        this.setResult(BaseCode.STATUS, intent);
        finish();
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午1:41
 *
 */

package com.febers.iuestc.module.user.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.febers.iuestc.R;
import com.febers.iuestc.view.adapter.AdapterUser;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.entity.BeanUser;
import com.febers.iuestc.entity.BeanUserItem;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.module.user.presenter.UserContract;
import com.febers.iuestc.module.user.presenter.UserPresenterImpl;
import com.febers.iuestc.view.custom.CustomListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends BaseSwipeActivity implements UserContract.View {

    private List<BeanUserItem> userItemList = new ArrayList<>();
    private AdapterUser adapterUser;
    private CustomListView lvUserDetail;
    private SmartRefreshLayout smartRefreshLayout;
    BeanUserItem item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12;
    @Override
    protected int setView() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_user);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        smartRefreshLayout = findViewById(R.id.srl_user);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener( (RefreshLayout refreshLayout) -> {
            dateRequest(true);
        });

        lvUserDetail = findViewById(R.id.list_view_user);
        adapterUser = new AdapterUser(this,
                R.layout.item_user_detail, initList());
        lvUserDetail.setAdapter(adapterUser);
        dateRequest(false);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        UserContract.Presenter presenter = new UserPresenterImpl(this);
        presenter.userDetailRequest(isRefresh);
    }

    @Override
    public void showUserDetail(BaseEvent<BeanUser> event) {
        if (event.getCode() == BaseCode.UPDATE) {
            smartRefreshLayout.finishRefresh(true);
        }
        BeanUser user = event.getDate();
        if (event.getCode() == BaseCode.LOCAL && user.getChineseName().isEmpty()) {
            smartRefreshLayout.autoRefresh();
            return;
        }
        item1.setValue(user.getChineseName());
        item2.setValue(user.getEnglishName());
        item3.setValue(user.getId());
        item4.setValue(user.getType());
        item5.setValue(user.getSchool());
        item6.setValue(user.getMajor());
        item7.setValue(user.getUserClass());
        item8.setValue(user.getIntoTime());
        item9.setValue(user.getOutTime());
        item10.setValue(user.getPosition());
        item11.setValue(user.getPhone());
        item12.setValue(user.getAddress());
        runOnUiThread( () -> {
            adapterUser.notifyDataSetChanged();
        });
    }

    @Override
    public void statusToFail() {
        smartRefreshLayout.finishRefresh(false);
        startActivityForResult(new Intent(UserActivity.this, LoginActivity.class), BaseCode.STATUS);
    }

    @Override
    public void onError(String error) {
        smartRefreshLayout.finishRefresh(false);
        super.onError(error);
    }

    private List<BeanUserItem> initList() {
        item1 = new BeanUserItem("中文名", "...");
        item2 = new BeanUserItem("英文名", "...");
        item3 = new BeanUserItem("学号", "...");
        item4 = new BeanUserItem("学历", "...");
        item5 = new BeanUserItem("学院", "...");
        item6 = new BeanUserItem("专业", "...");
        item7 = new BeanUserItem("班级", "...");
        item8 = new BeanUserItem("入校时间", "...");
        item9 = new BeanUserItem("毕业时间", "...");
        item10 = new BeanUserItem("校区", "...");
        item11 = new BeanUserItem("移动电话", "...");
        item12 = new BeanUserItem("通讯地址", "...");
        userItemList.clear();
        userItemList.add(item1);
        userItemList.add(item2);
        userItemList.add(item3);
        userItemList.add(item4);
        userItemList.add(item5);
        userItemList.add(item6);
        userItemList.add(item7);
        userItemList.add(item8);
        userItemList.add(item9);
        userItemList.add(item10);
        userItemList.add(item11);
        userItemList.add(item12);
        return userItemList;
    }
}

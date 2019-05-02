/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-21 下午8:11
 *
 */

package com.febers.iuestc.module.service.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.MenuItem;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.module.service.presenter.CalPresenterImpl;
import com.febers.iuestc.module.service.presenter.SchoolCalendarContact;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ToastUtil;
import com.febers.iuestc.view.custom.PinchImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public class CalActivity extends BaseSwipeActivity implements SchoolCalendarContact.View {

    private SchoolCalendarContact.Presenter calPresenter = new CalPresenterImpl(this);
    private PinchImageView imageViewCal;

    @Override
    protected int setView() {
        return R.layout.activity_cal;
    }

    @Override
    protected int setMenu() {
        return R.menu.calender_menu;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_calender);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        imageViewCal = findViewById(R.id.imgview_calender);
        if (!SPUtil.getInstance()
                .get(MyApp.getContext().getString(R.string.sp_get_calender), false)) {
            dataRequest(true);
        } else {
            ToastUtil.showShortToast("正在加载本地校历");
            dataRequest(false);
        }
    }


    @Override
    public void dataRequest(Boolean isRefresh) {
        if (isRefresh) {
            showProgressDialog();
        }
        calPresenter.calendarRequest(isRefresh);
    }

    @Override
    public void showCalender(BaseEvent baseEvent) {
        dismissProgressDialog();
        Bitmap bitmap = (Bitmap) baseEvent.getDate();
        int eventCode = baseEvent.getCode();
        if (eventCode == BaseCode.ERROR) {
            onError("获取校历出错，请刷新或访问源网页");
            return;
        }
        runOnUiThread( () ->
                imageViewCal.setImageBitmap(bitmap)
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_calender_refresh:
                dataRequest(true);
                break;
            case R.id.item_calender_open_web:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.jwc.uestc.edu.cn/web/News!view.action?id=1224"));
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

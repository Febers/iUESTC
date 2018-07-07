/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.mvp.presenter.CalPresenterImpl;
import com.febers.iuestc.mvp.presenter.SchoolCalendarContact;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.view.custom.PinchImageView;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

public class CalActivity extends BaseActivity implements SchoolCalendarContact.View {

    private SchoolCalendarContact.Presenter calPresenter = new CalPresenterImpl(this);
    private PinchImageView imageViewCal;

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
            .edge(true)
            .build();
        Slidr.attach(this, config);
        return R.layout.activity_cal;
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
        if (!CustomSharedPreferences.getInstance()
                .get(BaseApplication.getContext().getString(R.string.sp_get_calender), false)) {
            getCalender(true);
            return;
        }
        getCalender(false);
    }

    private void getCalender(Boolean isRefresh) {
        if (isRefresh) {
            showProgressDialog();
        }
        calPresenter.calendarRequest(isRefresh);
    }

    @Override
    public void showCalender(final Bitmap bitmap) {
        dismissProgressDialog();
        runOnUiThread( () ->
                imageViewCal.setImageBitmap(bitmap)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calender_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_calender_refresh:
                getCalender(true);
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

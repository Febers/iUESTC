/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午6:23
 *
 */

package com.febers.iuestc.module.ecard.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterRecord;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.module.ecard.presenter.ECardContract;
import com.febers.iuestc.entity.BeanECardPayRecord;
import com.febers.iuestc.module.ecard.presenter.ECardPresenterImp;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import java.util.List;

public class ECardRecrodActivity extends BaseActivity implements ECardContract.View{

    private Toolbar mToolbar;
    private RecyclerView mRVECardeRecord;
    private AdapterRecord mAdapterRecord;
    private ECardContract.Presenter mECardPresenter = new ECardPresenterImp(this);

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
                .edge(true)
                .build();
        Slidr.attach(this, config);
        return R.layout.activity_ecard_recrod;
    }

    @Override
    protected void findViewById() {
        mToolbar = findViewById(R.id.tb_ecard_record);
        mRVECardeRecord = findViewById(R.id.rv_ecard_record_activity);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mRVECardeRecord.setLayoutManager(new LinearLayoutManager(this));
        new Thread(()-> {
            getRecord();
        }).start();
    }

    private void getRecord() {
        mECardPresenter.localDataRequest(100);
    }

    @Override
    public void showLoginResult(String msg) {
    }

    @Override
    public void showECardBalance(String balance) {
    }

    @Override
    public void showElecBalance(String balance) {
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

    @Override
    public void showPayRecord(List<BeanECardPayRecord.data.consumes> consumesList) {
        runOnUiThread(()-> {
            mAdapterRecord = new AdapterRecord(consumesList);
            mRVECardeRecord.setAdapter(mAdapterRecord);
        });
    }
}

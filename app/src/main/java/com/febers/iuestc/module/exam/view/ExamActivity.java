/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.exam.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterExam;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.exam.presenter.ExamContract;
import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.module.exam.presenter.ExamPresenterImpl;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * mType 1为期末考试， 2为期中考试
 */
public class ExamActivity extends BaseActivity implements ExamContract.View{

    private static final String TAG = "ExamActivity";
    private RecyclerView rvExam;
    private AdapterExam adapterExam;
    private List<BeanExam> mExamList = new ArrayList<>();
    private Toolbar toolbar;
    private SmartRefreshLayout smartRefreshLayout;
    private ExamContract.Presenter examPresenter = new ExamPresenterImpl(this);
    private RadioGroup rgExam;
    private int mType = 1;
    private String examName = "exam_1";

    @Override
    protected int setView() {
        return R.layout.activity_exam;
    }

    @Override
    protected void findViewById() {
        toolbar = findViewById(R.id.tb_exam);
        rvExam = findViewById(R.id.rv_exam);
        rgExam = findViewById(R.id.rg_exam);
        smartRefreshLayout = findViewById(R.id.srl_exam);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("我的考试");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        rvExam.setLayoutManager(new LinearLayoutManager(this));
        rvExam.setNestedScrollingEnabled(false);
        rgExam.setOnCheckedChangeListener( (RadioGroup group, int checkedId) -> {
                switch (checkedId) {
                    case R.id.rb_exam_final:
                        mType = 1;
                        getExam(false, mType);
                        break;
                    case R.id.rb_exam_middle:
                        mType = 2;
                        getExam(false, mType);
                        break;
                    default:
                        break;
                }
        });
        if (!CustomSharedPreferences.getInstance().get("exam_1", false)) {
            getExam(true, mType);
        } else {
            getExam(false, mType);
        }
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener( (RefreshLayout refreshLayout) -> {
           getExam(true, mType);
        });
    }

    @Override
    public void showExam(BaseEvent<List<BeanExam>> event) {
        mExamList.clear();
        mExamList.addAll(event.getDate());
        runOnUiThread( () -> {
            adapterExam = new AdapterExam(mExamList);
            rvExam.setAdapter(adapterExam);
            if (event.getCode() == BaseCode.UPDATE) {
                smartRefreshLayout.finishRefresh(true);
            }
        });
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        getExam(isRefresh, mType);
    }

    private void getExam(Boolean isRefresh, int type) {
        if (isRefresh) {
            if (!BaseApplication.checkNetConnecting()) {
                onError("当前网络不可用");
                return;
            }
        }
        if (type == 2) {
            examName = "exam_2";
        } else if (type == 1) {
            examName = "exam_1";
        }
//        if ((!CustomSharedPreferences.getInstance().get(examName, false)) || (isRefresh)) {
//            showProgressDialog();
//        }
        examPresenter.examRequest(isRefresh, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exam_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_exam_refresh:
                getExam(true, mType);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void statusToFail() {
        smartRefreshLayout.finishRefresh(false);
        startActivityForResult(new Intent(ExamActivity.this, LoginActivity.class), BaseCode.STATUS);
    }

    @Override
    public void onError(String error) {
        smartRefreshLayout.finishRefresh(false);
        super.onError(error);
    }
}

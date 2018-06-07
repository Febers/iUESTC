/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.module.exam.view;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterExam;
import com.febers.iuestc.module.exam.contract.ExamContract;
import com.febers.iuestc.module.exam.model.BeanExam;
import com.febers.iuestc.module.exam.contract.ExamPresenterImp;
import com.febers.iuestc.utils.CustomSharedPreferences;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * mType 1为期末考试， 2为期中考试
 */
public class ExamActivity extends BaseActivity implements ExamContract.View{

    private static final String TAG = "ExamActivity";
    private RecyclerView rvExam;
    private LinearLayoutManager llmExam;
    private AdapterExam adapterExam;
    private List<BeanExam> mExamList = new ArrayList<>();
    private Toolbar toolbar;
    private ExamContract.Presenter examPresenter = new ExamPresenterImp(this);
    private RadioGroup rgExam;
    private RadioButton rbMiddle, rbFinal;
    private int mType = 1;
    private String examName = "exam_1";

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
                .edge(true)
                .build();
        Slidr.attach(this, config);
        return R.layout.activity_exam;
    }

    @Override
    protected void findViewById() {
        toolbar = findViewById(R.id.tb_exam);
        rvExam = findViewById(R.id.rv_exam);
        rbMiddle = findViewById(R.id.rb_exam_middle);
        rbFinal = findViewById(R.id.rb_exam_final);
        rgExam = findViewById(R.id.rg_exam);
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
    }

    @Override
    public void showExam(List<BeanExam> examList) {
        dismissProgressDialog();
        mExamList.clear();
        mExamList.addAll(examList);
        runOnUiThread( () -> {
            adapterExam = new AdapterExam(mExamList);
            rvExam.setAdapter(adapterExam);
        });
    }

    private void getExam(Boolean isRefresh, int type) {
        if (isRefresh) {
            if (!BaseApplication.checkNetConnecting()) {
                Toast.makeText(ExamActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (type == 2) {
            examName = "exam_2";
        } else if (type == 1) {
            examName = "exam_1";
        }
        if ((!CustomSharedPreferences.getInstance().get(examName, false)) || (isRefresh)) {
            showProgressDialog();
        }
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
            case android.R.id.home:
                finish();
                break;
            case R.id.item_exam_refresh:
                getExam(true, mType);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

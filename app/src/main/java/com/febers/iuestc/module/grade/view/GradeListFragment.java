/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.grade.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterGrade;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.grade.presenter.GradeContract;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.presenter.GradePresenterImpl;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.view.custom.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class GradeListFragment extends BaseFragment implements GradeContract.View{

    private static final String TAG = "GradeListFragment";
    private RecyclerView recyclerView;
    private Context context = BaseApplication.getContext();
    private AdapterGrade adapterGrade;
    private GradeContract.Presenter gradePresenter = new GradePresenterImpl(this);
    private List<BeanGrade> gradeList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_grade_list;
    }

    @Override
    protected void lazyLoad() {
        dateRequest(false);
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_grade_list);
        LinearLayoutManager llmGrade = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llmGrade);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        if (!CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_get_grade), false)) {
            isRefresh = true;
        }
        if (isRefresh) {
            if (!BaseApplication.checkNetConnecting()) {
                onError("当前网络不可用");
                return;
            }
            showProgressDialog();
        }
        gradePresenter.gradeRequest(isRefresh);
    }

    @Override
    public void showGrade(List<BeanGradeSummary> allGrades, List<BeanGrade> grades) {
        gradeList = grades;
        dismissProgressDialog();
        getActivity().runOnUiThread( () -> {
            adapterGrade = new AdapterGrade(gradeList);
            recyclerView.setAdapter(adapterGrade);
        });
    }

    void showGradeByTime(String time) {
        if (time == "ic_all_pink") {
            showGrade(new ArrayList<BeanGradeSummary>(), gradeList);
            return;
        }
        if (gradeList.size() == 0) {
            return;
        }
        final List<BeanGrade> tmpList = new ArrayList<>();
        for (int i = 0; i < gradeList.size(); i++) {
            if (gradeList.get(i).getSemester().contains(time)) {
                tmpList.add(gradeList.get(i));
            }
        }
        getActivity().runOnUiThread( () -> {
            adapterGrade = new AdapterGrade(tmpList);
            recyclerView.setAdapter(adapterGrade);
        });
    }

    @Override
    public void statusToFail() {
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), BaseCode.STATUS);
    }
}

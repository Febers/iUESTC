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
import android.os.Bundle;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.view.adapter.AdapterGrade;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.grade.presenter.GradeContract;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.presenter.GradePresenterImpl;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GradeListFragment extends BaseFragment implements GradeContract.View{

    private static final String TAG = "GradeListFragment";
    private RecyclerView recyclerView;
    private Context context = MyApp.getContext();
    private AdapterGrade adapterGrade;
    private GradeContract.Presenter gradePresenter = new GradePresenterImpl(this);
    private List<BeanGrade> mGradeList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_grade_list;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        dataRequest(false);
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_grade_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterGrade = new AdapterGrade(getContext(), mGradeList);
        recyclerView.setAdapter(adapterGrade);
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        if (!SPUtil.getInstance().get(context.getString(R.string.sp_get_grade), false)) {
            isRefresh = true;
        }
        if (isRefresh) {
            if (!MyApp.checkNetConnecting()) {
                onError("当前网络不可用");
                return;
            }
            showProgressDialog();
        }
        gradePresenter.gradeRequest(isRefresh);
    }

    @Override
    public void showGrade(List<BeanGradeSummary> allGrades, List<BeanGrade> grades) {
        mGradeList = grades;
        dismissProgressDialog();
        getActivity().runOnUiThread( () -> {
            adapterGrade.setNewData(mGradeList);
        });
    }

    void showGradeByTime(String time) {
        if (time.equals("ic_all_pink")) {
            showGrade(new ArrayList<>(), mGradeList);
            return;
        }
        if (mGradeList.size() == 0) {
            return;
        }
        final List<BeanGrade> tmpList = new ArrayList<>();
        for (int i = 0; i < mGradeList.size(); i++) {
            if (mGradeList.get(i).getSemester().contains(time)) {
                tmpList.add(mGradeList.get(i));
            }
        }
        getActivity().runOnUiThread( () -> {
            adapterGrade.setNewData(tmpList);
        });
    }

    @Override
    public void statusLoss() {
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), BaseCode.STATUS);
    }
}

package com.febers.iuestc.module.grade.view;

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

import static com.febers.iuestc.base.Constants.GRADE_GOT;

public class GradeListFragment extends BaseFragment implements GradeContract.View{

    private RecyclerView recyclerView;
    private AdapterGrade adapterGrade;
    private GradeContract.Presenter gradePresenter = new GradePresenterImpl(this);
    private List<BeanGrade> gradeList = new ArrayList<>();

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
        adapterGrade = new AdapterGrade(getContext(), gradeList);
        recyclerView.setAdapter(adapterGrade);
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        if (!SPUtil.INSTANCE().get(GRADE_GOT, false)) {
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
        gradeList = grades;
        dismissProgressDialog();
        getActivity().runOnUiThread( () -> {
            adapterGrade.setNewData(gradeList);
        });
    }

    void showGradeByTime(String time) {
        if (time.equals("ic_all_pink")) {
            showGrade(new ArrayList<>(), gradeList);
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
            adapterGrade.setNewData(tmpList);
        });
    }

    @Override
    public void statusLoss() {
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), BaseCode.STATUS);
    }
}

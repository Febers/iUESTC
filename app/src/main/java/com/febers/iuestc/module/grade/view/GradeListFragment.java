package com.febers.iuestc.module.grade.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterGrade;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.grade.contract.GradeContract;
import com.febers.iuestc.module.grade.model.BeanAllGrade;
import com.febers.iuestc.module.grade.model.BeanGrade;
import com.febers.iuestc.module.grade.contract.GradePresenterImp;
import com.febers.iuestc.utils.CustomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class GradeListFragment extends BaseFragment implements GradeContract.View{

    private static final String TAG = "GradeListFragment";
    private RecyclerView recyclerView;
    private Context context = BaseApplication.getContext();
    private AdapterGrade adapterGrade;
    private GradeContract.Presenter gradePresenter = new GradePresenterImp(this);
    private List<BeanGrade> gradeList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_grade_list;
    }

    @Override
    protected void lazyLoad() {
        getGrade(false);
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_grade_list);
        LinearLayoutManager llmGrade = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llmGrade);
    }

    public void getGrade(Boolean isRefresh) {
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
    public void showGrade(List<BeanAllGrade> allGrades, List<BeanGrade> grades) {
        gradeList = grades;
        dismissProgressDialog();
        getActivity().runOnUiThread( () -> {
            adapterGrade = new AdapterGrade(gradeList);
            recyclerView.setAdapter(adapterGrade);
        });
    }

    void showGradeByTime(String time) {
        if (time == "ic_all_pink") {
            showGrade(new ArrayList<BeanAllGrade>(), gradeList);
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
    protected void setPresenter() {
        presenter = gradePresenter;
    }
}

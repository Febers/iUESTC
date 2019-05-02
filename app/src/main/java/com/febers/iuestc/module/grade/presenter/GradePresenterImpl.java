package com.febers.iuestc.module.grade.presenter;

import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.model.GradeModelImpl;

import java.util.List;

public class GradePresenterImpl extends GradeContract.Presenter {

    public GradePresenterImpl(GradeContract.View view) {
        super(view);
    }

    private static final String TAG = "GradePresenterImpl";

    @Override
    public void gradeRequest(Boolean isRefresh) {
        GradeContract.Model gradeModel = new GradeModelImpl(this);
        gradeModel.gradeService(isRefresh, "");
    }

    @Override
    public void gradeResult(String status, List<BeanGradeSummary> allGrades, List<BeanGrade> gradeList) {
        if (view != null) {
            view.showGrade(allGrades, gradeList);
        }
    }
}

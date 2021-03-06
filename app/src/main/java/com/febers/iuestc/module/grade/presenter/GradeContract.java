package com.febers.iuestc.module.grade.presenter;

import com.febers.iuestc.base.edu.EduView;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;

import java.util.List;

public interface GradeContract {

    interface Model {
        void gradeService(Boolean isRefresh, String semester);
    }

    interface View extends EduView {
        void showGrade(List<BeanGradeSummary> allGrades, List<BeanGrade> grades);
    }

    abstract class Presenter extends BasePresenter<GradeContract.View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void gradeRequest(Boolean isRefresh);

        public abstract void gradeResult(String status, List<BeanGradeSummary> allGrades,
                                         List<BeanGrade> gradeList);
    }
}

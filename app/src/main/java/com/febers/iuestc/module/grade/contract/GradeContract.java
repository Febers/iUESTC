package com.febers.iuestc.module.grade.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.module.grade.model.BeanAllGrade;
import com.febers.iuestc.module.grade.model.BeanGrade;

import java.util.List;

public interface GradeContract {
    interface View extends BaseView {
        void showGrade(List<BeanAllGrade> allGrades, List<BeanGrade> grades);
    }

    abstract class Presenter extends BasePresenter<GradeContract.View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void gradeRequest(Boolean isRefresh);
        public abstract void gradeResult(String status, List<BeanAllGrade> allGrades, List<BeanGrade> gradeList);
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.grade.presenter;

import com.febers.iuestc.base.BaseEduView;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;

import java.util.List;

public interface GradeContract {

    interface Model {
        void gradeService(Boolean isRefresh, String semester) throws Exception;
    }

    interface View extends BaseEduView {
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

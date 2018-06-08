/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:26
 *
 */

package com.febers.iuestc.modules.grade.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.modules.grade.model.BeanAllGrade;
import com.febers.iuestc.modules.grade.model.BeanGrade;

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

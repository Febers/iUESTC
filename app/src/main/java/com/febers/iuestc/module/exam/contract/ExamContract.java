/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午6:45
 *
 */

package com.febers.iuestc.module.exam.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.module.exam.model.BeanExam;

import java.util.List;

public interface ExamContract {
    interface View extends BaseView {
        void showExam(List<BeanExam> examList);
    }
    abstract class Presenter extends BasePresenter<ExamContract.View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void examRequest(Boolean isRefresh, int type);
        public abstract void examResult(List<BeanExam> examList);
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.exam.presenter;

import com.febers.iuestc.base.BaseEduPresenter;
import com.febers.iuestc.base.BaseEduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.entity.BeanExam;

import java.util.List;

public interface ExamContract {
    interface View extends BaseEduView {
        void showExam(BaseEvent<List<BeanExam>> event);
    }
    abstract class Presenter extends BaseEduPresenter<View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void examRequest(Boolean isRefresh, int type);
        public abstract void examResult(BaseEvent<List<BeanExam>> event);
    }
}

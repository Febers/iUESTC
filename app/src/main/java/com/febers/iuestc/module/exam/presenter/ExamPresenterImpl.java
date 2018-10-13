/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.exam.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.module.exam.model.ExamModelImpl;

import java.util.List;

public class ExamPresenterImpl extends ExamContract.Presenter {

    private static final String TAG = "ExamPresenterImpl";

    public ExamPresenterImpl(ExamContract.View view) {
        super(view);
    }

    @Override
    public void examRequest(Boolean isRefresh, int type) {
        ExamContract.Model examModel = new ExamModelImpl(this);
        examModel.examService(isRefresh, type);
    }

    @Override
    public void examResult(BaseEvent<List<BeanExam>> event) {
        if (mEduView != null) {
            mEduView.showExam(event);
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.exam.presenter;

import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.module.exam.model.ExamModel;
import com.febers.iuestc.module.exam.model.IExamModel;

import java.util.List;

public class ExamPresenterImpl extends ExamContract.Presenter {

    private static final String TAG = "ExamPresenterImpl";

    public ExamPresenterImpl(ExamContract.View view) {
        super(view);
    }

    @Override
    public void examRequest(Boolean isRefresh, int type) {
        IExamModel examModel = new ExamModel(this);
        try {
            examModel.examService(isRefresh, type);
        } catch (Exception e) {
            e.printStackTrace();
            if (mView != null) {
                mView.onError("获取考试信息出现异常，请联系开发者");
            }
        }
    }

    @Override
    public void examResult(List<BeanExam> examList) {
        if (mView != null) {
            mView.showExam(examList);
        }
    }
}

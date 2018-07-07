/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.presenter;

import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.mvp.model.ExamModel;
import com.febers.iuestc.mvp.model.IExamModel;

import java.util.List;

public class ExamPresenterImpl extends ExamContract.Presenter {

    private static final String TAG = "ExamPresenterImpl";

    IExamModel examModel = new ExamModel(this);

    public ExamPresenterImpl(ExamContract.View view) {
        super(view);
    }

    @Override
    public void examRequest(Boolean isRefresh, int type) {
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

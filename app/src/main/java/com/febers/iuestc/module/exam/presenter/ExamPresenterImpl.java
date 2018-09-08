/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.exam.presenter;

import android.util.Log;

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
        ExamContract.IExamModel examModel = new ExamModelImpl(this);
        try {
            examModel.examService(isRefresh, type);
        } catch (Exception e) {
            e.printStackTrace();
            if (mEduView != null) {
                mEduView.onError("获取考试信息出现异常，请联系开发者");
            }
        }
    }

    @Override
    public void examResult(BaseEvent<List<BeanExam>> event) {
        if (mEduView != null) {
            mEduView.showExam(event);
        }
    }
}

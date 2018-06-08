/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午6:49
 *
 */

package com.febers.iuestc.modules.exam.contract;

import com.febers.iuestc.modules.exam.model.BeanExam;
import com.febers.iuestc.modules.exam.model.ExamModel;
import com.febers.iuestc.modules.exam.model.IExamModel;

import java.util.List;

public class ExamPresenterImp extends ExamContract.Presenter {

    private static final String TAG = "ExamPresenterImp";

    IExamModel examModel = new ExamModel(this);

    public ExamPresenterImp(ExamContract.View view) {
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

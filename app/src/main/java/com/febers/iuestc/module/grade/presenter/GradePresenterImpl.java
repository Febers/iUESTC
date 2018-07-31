/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.grade.presenter;

import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.model.GradeModel;
import com.febers.iuestc.module.grade.model.IGradeModel;

import java.util.List;

public class GradePresenterImpl extends GradeContract.Presenter {

    public GradePresenterImpl(GradeContract.View view) {
        super(view);
    }

    private static final String TAG = "GradePresenterImpl";

    @Override
    public void gradeRequest(Boolean isRefresh) {
        IGradeModel gradeModel = new GradeModel(this);
        try {
            gradeModel.gradeService(isRefresh, "");
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError("获取成绩出现错误");
        }
    }

    @Override
    public void gradeResult(String status, List<BeanGradeSummary> allGrades, List<BeanGrade> gradeList) {
        if (mView == null) {
            return;
        }
        mView.showGrade(allGrades, gradeList);
    }
}

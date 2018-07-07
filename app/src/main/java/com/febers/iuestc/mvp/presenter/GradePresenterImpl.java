/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午3:07
 *
 */

package com.febers.iuestc.mvp.presenter;

import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.mvp.model.GradeModel;
import com.febers.iuestc.mvp.model.IGradeModel;

import java.util.List;

public class GradePresenterImpl extends GradeContract.Presenter {

    public GradePresenterImpl(GradeContract.View view) {
        super(view);
    }

    private static final String TAG = "GradePresenterImpl";

    private IGradeModel gradeModel = new GradeModel(this);

    @Override
    public void gradeRequest(Boolean isRefresh) {
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

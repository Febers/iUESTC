/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.module.course.contract;

import android.util.Log;

import com.febers.iuestc.module.course.model.BeanCourse;
import com.febers.iuestc.module.course.model.CourseEventMessage;
import com.febers.iuestc.module.course.model.CourseModel;
import com.febers.iuestc.module.course.model.ICourseModel;

import java.util.ArrayList;
import java.util.List;

public class CoursePresenterImpl extends CourseContract.Presenter{

    private static final String TAG = "CoursePresenterImpl";
    private ICourseModel courseModel = new CourseModel(this);
    private List<BeanCourse> mBeanCourseList = new ArrayList<>();

    public CoursePresenterImpl(CourseContract.View view) {
        super(view);
    }

    @Override
    public void courseRequest(Boolean isRefresh) {
        try {
            courseModel.courseService(isRefresh);
        } catch (Exception e) {
            e.printStackTrace();
            if (mView != null) {
                mView.onError("获取课表出错, 请联系开发者");
            }
        }
    }

    //网络请求获得的课程列表
    @Override
    public void underCourseResult(String status, List<BeanCourse> beanCourseList) {
        mBeanCourseList = beanCourseList;
        if (mView != null) {
            Log.d(TAG, "underCourseResult: "+status);
            mView.showUnderCourse(new CourseEventMessage(status, beanCourseList));
        }
        mBeanCourseList.clear();
    }
}

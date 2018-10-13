/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.course.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.module.course.model.CourseModelImpl;

import java.util.List;

public class CoursePresenterImpl extends CourseContract.Presenter{

    public CoursePresenterImpl(CourseContract.View view) {
        super(view);
    }

    @Override
    public void courseRequest(Boolean isRefresh) {
        CourseContract.Model courseModel = new CourseModelImpl(this);
        courseModel.updateCourseService(isRefresh);
    }

    //网络请求获得的课程列表
    @Override
    public void underCourseResult(BaseEvent<List<BeanCourse>> event) {
        if (mEduView != null) {
            mEduView.showUnderCourse(event);
        }
    }
}

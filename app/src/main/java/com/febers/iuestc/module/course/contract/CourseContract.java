/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.module.course.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.module.course.model.BeanCourse;
import com.febers.iuestc.module.course.model.CourseEventMessage;

import java.util.List;

public interface CourseContract {

    interface View extends BaseView {
        void getCourse(Boolean isRefresh);
        void showUnderCourse(CourseEventMessage message);
    }

    abstract class Presenter extends BasePresenter<CourseContract.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void courseRequest(Boolean isRefresh);

        public abstract void underCourseResult(String status, List<BeanCourse> beanCourseList);
    }
}

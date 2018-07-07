/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午4:50
 *
 */

package com.febers.iuestc.mvp.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.entity.BeanCourse;

import java.util.List;

public interface CourseContract {

    interface View extends BaseView {
        void getCourse(Boolean isRefresh);
        void showUnderCourse(BaseEvent<List<BeanCourse>> event);
    }

    abstract class Presenter extends BasePresenter<CourseContract.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void courseRequest(Boolean isRefresh);
        public abstract void underCourseResult(BaseEvent<List<BeanCourse>> event);
    }
}

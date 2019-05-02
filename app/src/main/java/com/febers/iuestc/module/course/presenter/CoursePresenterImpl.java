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
        if (eduView != null) {
            eduView.showUnderCourse(event);
        }
    }
}

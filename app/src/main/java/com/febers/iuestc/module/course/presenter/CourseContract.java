package com.febers.iuestc.module.course.presenter;

import com.febers.iuestc.base.edu.EduPresenter;
import com.febers.iuestc.base.edu.EduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanCourse;

import java.util.List;

public interface CourseContract {

    interface Model {
        void updateCourseService(Boolean isRefresh);
    }

    interface View extends EduView {
        void showUnderCourse(BaseEvent<List<BeanCourse>> event);
    }

    abstract class Presenter extends EduPresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void courseRequest(Boolean isRefresh);

        public abstract void underCourseResult(BaseEvent<List<BeanCourse>> event);
    }
}

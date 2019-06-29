package com.febers.iuestc.module.exam.presenter;

import com.febers.iuestc.base.edu.EduPresenter;
import com.febers.iuestc.base.edu.EduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanExam;

import java.util.List;

public interface ExamContract {

    interface Model {
        void examService(Boolean isRefresh, int type);
    }

    interface View extends EduView {
        void showExam(BaseEvent<List<BeanExam>> event);
    }

    abstract class Presenter extends EduPresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void examRequest(Boolean isRefresh, int type);

        public abstract void examResult(BaseEvent<List<BeanExam>> event);
    }
}

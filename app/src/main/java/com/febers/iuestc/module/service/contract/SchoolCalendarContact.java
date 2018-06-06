package com.febers.iuestc.module.service.contract;

import android.graphics.Bitmap;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;

public interface SchoolCalendarContact {

    interface View extends BaseView {
        void showCalender(Bitmap bitmap);
    }

    abstract class Presenter extends BasePresenter<SchoolCalendarContact.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void calendarRequest(Boolean isRefresh);
        public abstract void calendarResult(Bitmap bitmap);
    }
}

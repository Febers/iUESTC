package com.febers.iuestc.module.service.presenter;

import android.graphics.Bitmap;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;

public interface CalenderContact {

    interface Model {
        void calendarService(Boolean isRefresh) throws Exception;
    }

    interface View extends BaseView {
        void showCalender(BaseEvent baseEvent);
    }

    abstract class Presenter extends BasePresenter<CalenderContact.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void calendarRequest(Boolean isRefresh);
        public abstract void calendarResult(BaseEvent<Bitmap> baseEvent);
    }
}

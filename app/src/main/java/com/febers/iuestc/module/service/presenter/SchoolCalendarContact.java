/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-21 下午8:14
 *
 */

package com.febers.iuestc.module.service.presenter;

import android.graphics.Bitmap;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;

public interface SchoolCalendarContact {

    interface View extends BaseView {
        void showCalender(BaseEvent baseEvent);
    }

    abstract class Presenter extends BasePresenter<SchoolCalendarContact.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void calendarRequest(Boolean isRefresh);
        public abstract void calendarResult(BaseEvent<Bitmap> baseEvent);
    }
}

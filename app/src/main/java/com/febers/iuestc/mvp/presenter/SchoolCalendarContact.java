/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.presenter;

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

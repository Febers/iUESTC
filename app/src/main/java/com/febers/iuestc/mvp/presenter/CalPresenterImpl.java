/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.presenter;

import android.graphics.Bitmap;

import com.febers.iuestc.mvp.model.CalModel;
import com.febers.iuestc.mvp.model.ICalModel;

public class CalPresenterImpl extends SchoolCalendarContact.Presenter {

    private ICalModel calModel = new CalModel(this);

    public CalPresenterImpl(SchoolCalendarContact.View view) {
        super(view);
    }

    @Override
    public void calendarRequest(Boolean isRefresh) {
        try {
            calModel.getCalendar(isRefresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calendarResult(Bitmap bitmap) {
        if (mView != null) {
            mView.showCalender(bitmap);
        }
    }
}

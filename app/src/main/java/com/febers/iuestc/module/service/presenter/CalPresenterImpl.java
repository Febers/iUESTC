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
import com.febers.iuestc.module.service.model.CalModel;
import com.febers.iuestc.module.service.model.ICalModel;

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
    public void calendarResult(BaseEvent<Bitmap> baseEvent) {
        if (mView != null) {
            mView.showCalender(baseEvent);
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午9:37
 *
 */

package com.febers.iuestc.module.service.contract;

import android.graphics.Bitmap;

import com.febers.iuestc.module.service.model.CalModel;
import com.febers.iuestc.module.service.model.ICalModel;

public class CalPresenterImp extends SchoolCalendarContact.Presenter {

    private ICalModel calModel = new CalModel(this);

    public CalPresenterImp(SchoolCalendarContact.View view) {
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

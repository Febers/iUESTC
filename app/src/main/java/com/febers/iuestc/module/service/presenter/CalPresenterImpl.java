package com.febers.iuestc.module.service.presenter;

import android.graphics.Bitmap;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.service.model.CalenderModelImpl;

public class CalPresenterImpl extends CalenderContract.Presenter {

    private CalenderContract.Model calModel = new CalenderModelImpl(this);

    public CalPresenterImpl(CalenderContract.View view) {
        super(view);
    }

    @Override
    public void calendarRequest(Boolean isRefresh) {
        try {
            calModel.calendarService(isRefresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void calendarResult(BaseEvent<Bitmap> baseEvent) {
        if (view != null) {
            view.showCalender(baseEvent);
        }
    }
}

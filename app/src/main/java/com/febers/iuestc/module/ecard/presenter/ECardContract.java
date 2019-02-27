/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-5 上午2:46
 *
 */

package com.febers.iuestc.module.ecard.presenter;

import com.febers.iuestc.edu.EduPresenter;
import com.febers.iuestc.edu.EduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanEduECard;

public interface ECardContract {

    interface Model {
        void localDataService();
        void resolveHtmlService(String html);
    }

    interface View extends EduView {
        void showHomePageResult(BaseEvent event);
        void showDetailPageResult(BaseEvent<BeanEduECard> event);
    }

    abstract class Presenter extends EduPresenter<View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void localDateRequest();
        public abstract void homePageResult(BaseEvent event);
        public abstract void DetailPageResult(BaseEvent<BeanEduECard> event);
    }
}

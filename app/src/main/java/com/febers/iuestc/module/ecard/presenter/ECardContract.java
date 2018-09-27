/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-5 上午2:46
 *
 */

package com.febers.iuestc.module.ecard.presenter;

import com.febers.iuestc.base.BaseEduPresenter;
import com.febers.iuestc.base.BaseEduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanEduECard;

public interface ECardContract {

    interface Model {
        void localDataService() throws Exception;
        void resolveHtmlService(String html) throws Exception;
    }

    interface View extends BaseEduView {
        void showHomePageResult(BaseEvent event);
        void showDetailPageResult(BaseEvent<BeanEduECard> event);
    }

    abstract class Presenter extends BaseEduPresenter<ECardContract.View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void localDateRequest();
        public abstract void homePageResult(BaseEvent event);
        public abstract void DetailPageResult(BaseEvent<BeanEduECard> event);
    }
}

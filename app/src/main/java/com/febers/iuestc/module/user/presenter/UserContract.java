/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-23 上午11:29
 *
 */

package com.febers.iuestc.module.user.presenter;

import com.febers.iuestc.edu.EduPresenter;
import com.febers.iuestc.edu.EduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanUser;

public interface UserContract {

    interface Model {
        void userDetailService(Boolean isRefresh) throws Exception;
    }

    interface View extends EduView {
        void showUserDetail(BaseEvent<BeanUser> event);
    }

    abstract class Presenter extends EduPresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void userDetailRequest(Boolean isRefresh);
        public abstract void userDetailResult(BaseEvent<BeanUser> event);
    }
}

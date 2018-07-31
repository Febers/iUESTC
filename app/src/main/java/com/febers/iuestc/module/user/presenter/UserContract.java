/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-23 上午11:29
 *
 */

package com.febers.iuestc.module.user.presenter;

import com.febers.iuestc.base.BaseEduPresenter;
import com.febers.iuestc.base.BaseEduView;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;

public interface UserContract {

    interface View extends BaseEduView {
        void showUserDetail(String name, String id);
    }

    abstract class Presenter extends BaseEduPresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void userDetailRequest();
    }
}

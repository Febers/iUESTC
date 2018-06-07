/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 下午10:45
 *
 */

package com.febers.iuestc.module.user.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;

public interface UserContract {

    interface View extends BaseView {
        void showUserDetail(String name, String id);
    }

    abstract class Presenter extends BasePresenter<UserContract.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void userDetailRequest();
    }
}

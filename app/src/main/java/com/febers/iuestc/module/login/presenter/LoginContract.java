/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.login.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.module.login.model.LoginResult;

public interface LoginContract {

    interface View extends BaseView{
        void loginSuccess(BaseEvent event);
        void loginFail(String failMsg);
        void loginError(String errorMsg);
    }

    abstract class Presenter extends BasePresenter<LoginContract.View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void loginResult(BaseEvent event);
    }
}

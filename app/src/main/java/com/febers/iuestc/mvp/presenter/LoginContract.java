/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.presenter;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.mvp.model.LoginResult;

public interface LoginContract {

    interface View extends BaseView{
        void loginSuccess();
        void loginFail(String failMsg);
        void loginError(String errorMsg);
    }

    abstract class Presenter extends BasePresenter<LoginContract.View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract void loginRequest(String id, String pw);
        public abstract void loginResult(LoginResult loginResult);
    }
}

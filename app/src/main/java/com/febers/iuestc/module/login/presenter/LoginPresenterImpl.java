/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-15 上午10:34
 *
 */

package com.febers.iuestc.module.login.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.model.LoginModelImpl;

public class LoginPresenterImpl extends LoginContract.Presenter {

    public LoginPresenterImpl(LoginContract.View view) {
        super(view);
    }

    @Override
    public void loginRequest(String id, String pw) {
        LoginContract.Model model = new LoginModelImpl(this);
        try {
            model.loginService(id, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginResult(BaseEvent<String> event) {
        if (mEduView != null) {
            mEduView.loginResult(event);
        }
    }
}

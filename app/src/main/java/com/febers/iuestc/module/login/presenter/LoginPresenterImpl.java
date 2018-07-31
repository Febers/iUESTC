/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.login.presenter;

import android.util.Log;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.model.ILoginModel;
import com.febers.iuestc.module.login.model.LoginModel;
import com.febers.iuestc.module.login.model.LoginResult;

public class LoginPresenterImpl extends LoginContract.Presenter {

    private static final String TAG = "LoginPresenterImpl";

    private ILoginModel mLoginModel = new LoginModel(this);

    public LoginPresenterImpl(LoginContract.View view) {
        super(view);
    }

    @Override
    public void loginRequest(String id, String pw) {
        try {
            mLoginModel.loginService(id, pw);
        } catch (Exception e) {
            e.printStackTrace();
            if (mView != null) {
                mView.loginError("登录出现异常");
            }
        }
    }

    @Override
    public void loginResult(LoginResult loginResult) {
        if (mView == null) {
            return;
        }
        switch (loginResult) {
            case LOGIN_SUCCESS:
                mView.loginSuccess(new BaseEvent(0, ""));
                Log.d(TAG, "loginResult: 0");
                break;
            case LOGIN_CONDITION_FAIL:
                mView.loginFail("获取登录条件失败");
                Log.d(TAG, "loginResult: 1");
                break;
            case LOGIN_PW_FAIL:
                mView.loginFail("密码错误");
                Log.d(TAG, "loginResult: 2");
                break;
            case LOGIN_RESOLVE_RESULT_FAIL:
                mView.loginError("解析登陆结果出错");
                break;
            case LOGIN_TIME_OUT:
                mView.loginError("登录超时");
                break;
            case UNKNOWE_FAIL:
                mView.loginError("未知异常");
                break;
            case LOGIN_TIMEOUT:
                mView.loginError("网络请求超时");
                break;
            default:
                break;
        }
    }
}

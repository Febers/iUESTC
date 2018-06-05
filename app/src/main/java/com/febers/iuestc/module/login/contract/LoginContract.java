package com.febers.iuestc.module.login.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.module.login.model.LoginResult;

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

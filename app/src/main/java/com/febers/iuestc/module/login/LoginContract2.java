package com.febers.iuestc.module.login;

import com.febers.iuestc.base.BaseEvent;

public interface LoginContract2 {

    interface Model {
        void loginService(String userName, String password);
    }

    interface View {
        void showLoginResult(BaseEvent<String> event);
    }

    interface Presenter {

    }
}

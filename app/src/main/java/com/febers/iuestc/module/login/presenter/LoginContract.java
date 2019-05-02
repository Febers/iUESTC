package com.febers.iuestc.module.login.presenter;

import com.febers.iuestc.edu.EduPresenter;
import com.febers.iuestc.edu.EduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;

public interface LoginContract {

    interface Resolver {
        void resolve(String html);
    }

    abstract class Model extends BaseModel<EduPresenter> {
        public abstract void loginService(String id , String pw) throws Exception;
    }

    interface View extends EduView {
        default void loadIdAndPwFunc(BaseEvent<String> event){ }
        void loginResult(BaseEvent<String> event);
    }

    /*
     * P类，其中sendIdAndPwFunc 是WebView登录时的方法
     * loginRequest 是自定义登录时的方法
     */
    abstract class Presenter extends EduPresenter<View> {

        public Presenter(View view) {
            super(view);
        }

        public void sendIdAndPwFunc(BaseEvent<String> event){}

        public abstract void loginResult(BaseEvent<String> event);

        public void loginRequest(String id , String pw){}
    }
}

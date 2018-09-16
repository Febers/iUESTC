/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.login.presenter;

import com.febers.iuestc.base.BaseEduPresenter;
import com.febers.iuestc.base.BaseEduView;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;

public interface LoginContract {

    interface Resolver {
        void resolve(String html);
    }

    abstract class Model extends BaseModel<Presenter>{
        public Model(Presenter p) {super(p);}
        public Model(){}
        public abstract void loginService(String id , String pw) throws Exception;
        public abstract Boolean reloginService() throws Exception;
    }

    interface View extends BaseEduView {
        default void loadIdAndPwFunc(BaseEvent<String> event){}
        void loginResult(BaseEvent<String> event);
    }

    /*
     * P类，其中sendIdAndPwFunc 是WebView登录时的方法
     * loginRequest 是自定义登录时的方法
     */
    abstract class Presenter extends BaseEduPresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        public void sendIdAndPwFunc(BaseEvent<String> event){}
        public void loginRequest(String id , String pw){}
        public abstract void loginResult(BaseEvent<String> event);
    }
}

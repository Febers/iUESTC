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

package com.febers.iuestc.module.user.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanUser;
import com.febers.iuestc.module.user.model.UserModelImpl;

/**
 * Created by 23033 on 2018/3/27.
 */

public class UserPresenterImpl extends UserContract.Presenter{

    public UserPresenterImpl(UserContract.View view) {
        super(view);
    }

    @Override
    public void userDetailRequest(Boolean isRefresh) {
        UserContract.Model userModel = new UserModelImpl(this);
        try {
            userModel.userDetailService(isRefresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userDetailResult(BaseEvent<BeanUser> event) {
        eduView.showUserDetail(event);
    }
}

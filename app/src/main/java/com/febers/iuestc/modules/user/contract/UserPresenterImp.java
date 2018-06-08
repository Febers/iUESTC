/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.modules.user.contract;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.utils.CustomSharedPreferences;

/**
 * Created by 23033 on 2018/3/27.
 */

public class UserPresenterImp extends UserContract.Presenter{


    public UserPresenterImp(UserContract.View view) {
        super(view);
    }

    @Override
    public void userDetailRequest() {
        Boolean isLogin = CustomSharedPreferences.getInstance().get(BaseApplication.getContext()
                .getString(R.string.sp_is_login), false);
        if (isLogin) {
            String name = "已登录";
            String id = CustomSharedPreferences.getInstance().get(BaseApplication.getContext()
                    .getString(R.string.sp_user_id), "");
            if (mView != null) {
                mView.showUserDetail(name, id);
            }
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-23 上午11:29
 *
 */

package com.febers.iuestc.module.user.presenter;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.module.user.presenter.UserContract;
import com.febers.iuestc.util.CustomSharedPreferences;

/**
 * Created by 23033 on 2018/3/27.
 */

public class UserPresenterImpl extends UserContract.Presenter{


    public UserPresenterImpl(UserContract.View view) {
        super(view);
    }

    @Override
    public void userDetailRequest() {

    }
}
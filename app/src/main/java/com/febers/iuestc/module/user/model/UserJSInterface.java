/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-31 下午4:51
 *
 */

package com.febers.iuestc.module.user.model;

import android.util.Log;

import com.febers.iuestc.base.BaseJSInterface;
import com.febers.iuestc.module.user.presenter.UserContract;

public class UserJSInterface implements BaseJSInterface {

    private static final String TAG = "UserJSInterface";
    private UserContract.View view;

    public UserJSInterface(UserContract.View view) {
        this.view = view;
    }

    @Override
    public void processHTML(String html) {

    }
}

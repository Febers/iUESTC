/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午12:57
 *
 */

package com.febers.iuestc.base;

import android.util.Log;

public abstract class BasePresenter<V extends BaseView> {

    protected V mView;
    private static final String TAG = "BasePresenter";

    public BasePresenter(V view) {
        mView = view;
    }

    public void errorResult(String error) {
        mView.onError(error);
    }
}

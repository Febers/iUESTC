/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-29 下午1:32
 *
 */

package com.febers.iuestc.base;

public abstract class BaseEduPresenter<V extends BaseEduView> extends BasePresenter{

    protected V mEduView;

    public BaseEduPresenter(V view) {
        super(view);
        mEduView = view;
    }

    public void loginStatusFail() {
        mEduView.statusToFail();
    }
}

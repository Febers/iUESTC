/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-29 下午1:32
 *
 */

package com.febers.iuestc.edu;

import com.febers.iuestc.base.BasePresenter;

public abstract class EduPresenter<V extends EduView> extends BasePresenter {

    protected V mEduView;

    public EduPresenter(V view) {
        super(view);
        mEduView = view;
    }

    public void loginStatusFail() {
        mEduView.statusToFail();
    }
}

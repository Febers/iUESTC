package com.febers.iuestc.edu;

import com.febers.iuestc.base.BasePresenter;

public abstract class EduPresenter<V extends EduView> extends BasePresenter {

    protected V eduView;

    public EduPresenter(V view) {
        super(view);
        eduView = view;
    }

    public void loginStatusLoss() {
        eduView.statusLoss();
    }
}

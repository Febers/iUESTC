package com.febers.iuestc.base;

public abstract class BasePresenter<V extends BaseView> {

    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

    protected void dettchView() {
        mView = null;
    }

    public void errorResult(String error) {
        mView.onError(error);
    }
}

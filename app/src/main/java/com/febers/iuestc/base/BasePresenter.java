package com.febers.iuestc.base;


public abstract class BasePresenter<V extends BaseView> {

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    public void errorResult(String error) {
        view.onError(error);
    }
}

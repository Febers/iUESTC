package com.febers.iuestc.base;


import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView> {

    protected V view;
    protected WeakReference viewReference;

    public BasePresenter(V view) {
        this.view = view;
    }

    public void errorResult(String error) {
        view.onError(error);
    }
}

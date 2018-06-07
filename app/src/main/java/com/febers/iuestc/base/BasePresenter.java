package com.febers.iuestc.base;

public abstract class BasePresenter<V extends BaseView> implements IPresenter{

    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void errorResult(String error) {
        mView.onError(error);
    }
}

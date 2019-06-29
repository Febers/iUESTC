package com.febers.iuestc.base;

public interface BaseView {
    default void dataRequest(Boolean isRefresh) { }   //拓展方法，子类可重写，也可不重写
    void onError(String error);
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 上午10:01
 *
 */

package com.febers.iuestc.base;

public interface BaseView {
    default void dataRequest(Boolean isRefresh){}   //拓展方法，子类可重写，也可不重写
    void onError(String error);
}

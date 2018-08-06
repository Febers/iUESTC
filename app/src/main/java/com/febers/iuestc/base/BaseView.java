/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 上午10:01
 *
 */

package com.febers.iuestc.base;

public interface BaseView {
    void dateRequest(Boolean isRefresh);
    void onError(String error);
}

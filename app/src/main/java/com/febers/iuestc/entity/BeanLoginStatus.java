/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-15 上午11:37
 *
 */

package com.febers.iuestc.entity;

public class BeanLoginStatus {
    private Boolean toWebView;

    public BeanLoginStatus(Boolean toWebView) {
        this.toWebView = toWebView;
    }

    public Boolean getToWebView() {
        return toWebView;
    }

    public void setToWebView(Boolean toWebView) {
        this.toWebView = toWebView;
    }
}

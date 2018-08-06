/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-3 下午3:15
 *
 */

package com.febers.iuestc.entity;

public class BeanUserItem {
    private String param;
    private String value;

    public BeanUserItem(String param, String value) {
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

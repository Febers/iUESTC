/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:02
 *
 */

package com.febers.iuestc.module.user.model;

/**
 * “我的”界面设置选项list的类
 * Created by Febers on 2018/2/4.
 */

public class BeanSetting {

    private String name;

    private int imageId;

    public BeanSetting(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-2 上午3:40
 *
 */

package com.febers.iuestc.entity;

public class BeanTheme {
    private int color;
    private String name;
    private Boolean isUsing = false;

    public BeanTheme(int color, String name) {
        this.color = color;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }
}

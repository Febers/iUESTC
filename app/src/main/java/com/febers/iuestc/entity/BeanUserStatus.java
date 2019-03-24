package com.febers.iuestc.entity;

import com.febers.iuestc.base.BaseCode;

public class BeanUserStatus {

    private int status = BaseCode.UPDATE;

    public BeanUserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

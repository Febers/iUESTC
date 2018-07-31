/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-29 下午12:49
 *
 */

package com.febers.iuestc.module.login.model;

/**
 * 一个登录状态改变的接口
 * 实现了本接口的类，必须在接收到登录失效之后跳转到登录界面
 * 由处理数据的Model层调用
 */
public interface LoginStatusReciever {
    void statusToSuccess();
    void statusToFail();
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 下午1:09
 *
 */

package com.febers.iuestc.module.login.model;

/**
 * Created by 23033 on 2018/3/25.
 */

public interface ILoginModel {
    void loginService(String id , String pw) throws Exception;
    Boolean reloginService() throws Exception;
}

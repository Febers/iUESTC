/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.login.model;

public interface BeforeILoginModel {
    void loginService(String id , String pw) throws Exception;
    Boolean reloginService() throws Exception;
}

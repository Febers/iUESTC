/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.mvp.model;

public interface IECardModel {
    void loginECardService(String phone, String pw) throws Exception;
    void balanceService() throws Exception;
    void localDataService(int recordSize);
}

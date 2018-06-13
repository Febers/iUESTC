/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午4:10
 *
 */

package com.febers.iuestc.module.ecard.model;

public interface IECardModel {
    void loginECardService(String phone, String pw) throws Exception;
    void balanceService() throws Exception;
    void localDataService(int recordSize);
}

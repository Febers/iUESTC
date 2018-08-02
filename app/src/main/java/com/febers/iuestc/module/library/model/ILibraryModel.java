/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.library.model;

public interface ILibraryModel {
    void readHistoryService(Boolean isRefresh, int page) throws Exception;
    void queryBookService(String keyword, int type,int page) throws Exception;
    void bookDetailService(String url) throws Exception;
}

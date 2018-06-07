/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:02
 *
 */

package com.febers.iuestc.module.library.model;

public interface ILibraryModel {
    void getReadHistory(Boolean isRefresh, int page) throws Exception;
    void queryBook(String keyword, String  type, String postion, int status, int page, String nextPage) throws Exception;
}

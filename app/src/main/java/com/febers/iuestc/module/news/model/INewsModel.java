/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午8:34
 *
 */

package com.febers.iuestc.module.news.model;

public interface INewsModel {
    void newsService(Boolean isRefresh, int type, int position) throws Exception;
}

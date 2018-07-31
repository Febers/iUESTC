/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.course.model;

public interface ICourseModel {
    void updateCourseService(Boolean isRefresh) throws Exception;
    void localCourseService()throws Exception;
}

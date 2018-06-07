/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.module.course.model;

public interface ICourseModel {
    void courseService(Boolean isRefresh) throws Exception;
    void loadLocalCourse();
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.grade.model;

public interface IGradeModel {
    void gradeService(Boolean isRefresh, String semester) throws Exception;
}

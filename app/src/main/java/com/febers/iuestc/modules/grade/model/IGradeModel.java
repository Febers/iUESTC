/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.modules.grade.model;

public interface IGradeModel {
    void gradeService(Boolean isRefresh, String semester) throws Exception;
}

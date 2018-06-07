/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 下午11:24
 *
 */

package com.febers.iuestc.module.course.model;

import com.febers.iuestc.module.course.model.BeanCourse;

import java.util.List;

/**
 * 课表的Event类
 * 使用链表传递课表信息
 */

public class CourseEventMessage {

    private List<BeanCourse> beanCourseList;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourseEventMessage(String status, List<BeanCourse> list) {
        this.status = status;
        beanCourseList = list;
    }

    public List<BeanCourse> getBeanCourseList() {
        return beanCourseList;
    }

    public void setBeanCourseList(List<BeanCourse> beanCourseList) {
        this.beanCourseList = beanCourseList;
    }
}

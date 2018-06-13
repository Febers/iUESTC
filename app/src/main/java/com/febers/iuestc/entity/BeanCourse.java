/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.entity;

public class BeanCourse {

    private static final String TAG = "BeanCourse";
    private String teacher = "";//课程老师
    private String name = "";    //课程名字
    private String classroom = "";   //教室
    private String week = "";    //周次
    private String time = "";   //时间，周一12节的格式为1 01，周二9、10节的格式为2 89

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BeanCourse(String teacher, String name, String classroom, String week, String time) {
        this.teacher = teacher;
        this.name = name;
        this.classroom = classroom;
        this.week = week;
        this.time = time;
    }

    public String getDetail() {
        return simpleName(name) + "\n" + "@"+ classroom;
    }

    /**
     * 去掉 电磁场与波(E0201440.12) 中括号的部分
     * @param oldName
     * @return
     */
    private String  simpleName(String oldName) {
        int start = oldName.indexOf("(");
        return oldName.substring(1,start);
    }
}

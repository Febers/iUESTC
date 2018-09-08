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
    private String rawWeek = "";  //原始week串
    private String week = "";    //周次
    private String day = "";
    private String time = "";   //时间，周一12节的格式为1 01，周二9、10节的格式为2 89
    private Boolean isRepeat = false;



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

    public String getRawWeek() {
        return rawWeek;
    }

    public void setRawWeek(String rawWeek) {
        this.rawWeek = rawWeek;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getRepeat() {
        return isRepeat;
    }

    public void setRepeat(Boolean repeat) {
        isRepeat = repeat;
    }

    public BeanCourse(String teacher, String name, String classroom, String rawWeek, String week, String day, String time) {
        this.teacher = teacher;
        this.name = name;
        this.classroom = classroom;
        this.rawWeek = rawWeek;
        this.week = week;
        this.day = day;
        this.time = time;
    }

    public String getDetail() {
        return simpleName(name) + "\n" + "@"+ classroom;
    }

    /**
     * 去掉 电磁场与波(E0201440.12) 中括号的部分
     * @param oldName  电磁场与波(E0201440.12)
     * @return  电磁场与波
     */
    private String  simpleName(String oldName) {
        int start = oldName.indexOf("(");
        return oldName.substring(0,start);
    }

    /**
     * 重写toString方法， 方便保存
     * @return
     */
    @Override
    public String toString() {
        return teacher + "#" + name + "#" + classroom + "#" + rawWeek + "#" + week + "#" + day + "#" + time;
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.module.grade.model;

import android.support.annotation.NonNull;

public class BeanAllGrade implements Comparable<BeanAllGrade>{
    private String year;
    private String semester;
    private String courseCount;
    private String allScore;
    private String averageGPA;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getAllScore() {
        return allScore;
    }

    public void setAllScore(String allScore) {
        this.allScore = allScore;
    }

    public String getAverageGPA() {
        return averageGPA;
    }

    public void setAverageGPA(String averageGPA) {
        this.averageGPA = averageGPA;
    }

    /**
     * 重写方法，为了直接按照学年排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NonNull BeanAllGrade o) {
        return Integer.valueOf((this.getYear()+this.getSemester()).replace("-",""))
                - Integer.valueOf((o.getYear()+o.getSemester()).replace("-",""));
    }
}

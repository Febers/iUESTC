/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.entity;

import android.support.annotation.NonNull;

public class BeanGradeSummary implements Comparable<BeanGradeSummary>{
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
    public int compareTo(@NonNull BeanGradeSummary o) {
        return Integer.valueOf((this.getYear()+this.getSemester()).replace("-",""))
                - Integer.valueOf((o.getYear()+o.getSemester()).replace("-",""));
    }
}

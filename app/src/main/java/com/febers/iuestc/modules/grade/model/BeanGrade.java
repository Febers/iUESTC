/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:02
 *
 */

package com.febers.iuestc.modules.grade.model;

public class BeanGrade implements Comparable<BeanGrade>{
    private String semester;
    private String courseCode;  //课程代码
    private String courseNum;   //课程序号
    private String courseName;
    private String courseType;
    private String studyScore;
    private String courseGPA;
    private String score;
    private String finalScore;

    public BeanGrade(){}
    public BeanGrade(String semester, String courseCode, String courseNum, String courseName, String courseType,
                     String studyScore, String score, String finalScore) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.courseType = courseType;
        this.studyScore = studyScore;
        this.score = score;
        this.finalScore = finalScore;
    }

    public String getCourseGPA() {
        return courseGPA;
    }

    public void setCourseGPA(String courseGPA) {
        this.courseGPA = courseGPA;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getStudyScore() {
        return studyScore;
    }

    public void setStudyScore(String studyScore) {
        this.studyScore = studyScore;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * 重写方法，按照学年排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(BeanGrade o) {
        return Integer.valueOf(this.getSemester().replace("-","").replace(" ",""))
                - Integer.valueOf(o.getSemester().replace("-","").replace(" ",""));
    }
}

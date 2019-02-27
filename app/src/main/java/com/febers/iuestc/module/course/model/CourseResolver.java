/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-7 下午4:05
 *
 */

package com.febers.iuestc.module.course.model;

import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.util.CourseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过网页源码解析获取课表
 */
public class CourseResolver {

    private static final String TAG = "CourseResolver";
    private static List<BeanCourse> mCourseList = new ArrayList<>();

    /**
     *
     * @param html  网页源码
     * @return mCourseList 课程列表
     * @throws Exception 如果解析条件不符，返回错误
     */
    static List<BeanCourse> resolveUnderCourseHtml(StringBuilder html) throws Exception{
        mCourseList.clear();
        /*
         * 从网页源码中截断有关课表的代码
         */
        int pStart = html.indexOf("activity = new TaskActivity");
        if (pStart == -1) {
            throw new Exception();
        }
        int pEnd = html.lastIndexOf("table0.marshalTable");
        String s1 = html.substring(pStart, pEnd);

        pStart = s1.indexOf("activity = new TaskActivity");
        pEnd = s1.lastIndexOf("activity = new TaskActivity");


        /*
         * 循环解析js函数体，然后获取每节课的片段
         * 当pStart == pEnd 时，说明只剩最后一节课的信息
         * partList为每节课信息的原始函数代码
         * 之后将每段代码交给getPerCourse处理以获取需要的数据
         */
        List<String> partList = new ArrayList<>();
        for (int pNext = 0; ; ) {
            pStart = pNext;
            if (pStart == pEnd) {
                partList.add(s1.substring(pEnd, s1.length()-1));
                break;
            }
            pNext = s1.indexOf("activity = new TaskActivity", pStart+1);
            partList.add(s1.substring(pStart, pNext-1));
        }

        for (int i = 0; i < partList.size(); i++) {
            getPerUnderCourse(new StringBuilder(partList.get(i)), i);
        }
        CourseStore.saveToFile(mCourseList);
        return mCourseList;
    }

    /**
     * 通过传入的每节课的字符串，提取每节课
     * 通过循环的方式先获取前几个属性
     * activity = new TaskActivity("10883","郭芙蕊","7126(C1800830.03)","哲学通论(C1800830.03)","353","第二教学楼203","01111111111111111110000000000000000000000000000000000");
        index =4*unitCount+8;
        table0.activities[index][table0.activities[index].length]=activity;
        index =4*unitCount+9;
        table0.activities[index][table0.activities[index].length]=activity;
        index =4*unitCount+10;
        table0.activities[index][table0.activities[index].length]=activity;
     */
    private static void getPerUnderCourse(StringBuilder stCourse, int i) {
        List<String> courseDetail = new ArrayList<>();
        try {
            int pStart = stCourse.indexOf("\"");
            int pNext = 0;
            for (int j = 0; j < 7; j++) {
                pNext = stCourse.indexOf("\"", pStart+1);
                //多个老师之间逗号隔开，会影响下面的解析，所以把出现的逗号都改成顿号
                courseDetail.add(j, stCourse.substring(pStart+1, pNext).replaceAll(",", "、"));
                pStart = stCourse.indexOf("\"", pNext+1);
            }
            int pEquote = stCourse.indexOf("=", pNext);
            Character day = stCourse.charAt(pEquote+1);
            courseDetail.add(7, day.toString());

            int pPlusStart = stCourse.indexOf("+", pNext);
            int pFenhao = stCourse.indexOf(";", pPlusStart);
            int pPlusEnd = stCourse.lastIndexOf("+");

            StringBuilder stTime = new StringBuilder();
            //限定节数最多为10
            for (int k = 0; k < 10; k++) {
                stTime.append(Integer.valueOf(stCourse.substring(pPlusStart + 1, pFenhao)) + 1);
                if (pPlusStart == pPlusEnd) {
                    break;
                }
                pPlusStart = stCourse.indexOf("+", pPlusStart+1);
                pFenhao = stCourse.indexOf(";", pPlusStart);
            }
            courseDetail.add(8, stTime.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        /*
         * 此时courseDetail为
         *
         * 构造器BeanCourse(String teacher, String name, String classroom, String rawWeek, String week, String day, String time)
         */
        BeanCourse beanCourse = new BeanCourse(courseDetail.get(1), courseDetail.get(3), courseDetail.get(5),
                courseDetail.get(6), CourseUtil.getSimpleWeeks(courseDetail.get(6)),
                courseDetail.get(7), courseDetail.get(8));
        mCourseList.add(beanCourse);
    }
}

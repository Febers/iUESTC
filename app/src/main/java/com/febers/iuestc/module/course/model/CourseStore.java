/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-7 下午4:06
 *
 */

package com.febers.iuestc.module.course.model;

import android.content.SharedPreferences;

import com.febers.iuestc.MyApplication;
import com.febers.iuestc.entity.BeanCourse;

import com.febers.iuestc.util.CustomSPUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 课表的保存类，保存在sp目录下的"local_course"文件中
 * 每节课以“bean_course”开头， 原始字符串以#分隔
 */
public class CourseStore {

    static void save(List<BeanCourse> courseList) {
        //保存课程数目
        CustomSPUtil.getInstance().put("course_count", courseList.size());
        SharedPreferences.Editor editor = MyApplication.getContext()
                .getSharedPreferences("local_course", 0).edit();
        editor.clear();
        editor.apply();
        for (int i = 0; i < courseList.size(); i++) {
            editor.putString("bean_course"+i, courseList.get(i).toString());
        }
        editor.apply();
    }

    static List<BeanCourse> get() {
        List<BeanCourse> courseList = new ArrayList<>();
        int course_count = CustomSPUtil.getInstance().get("course_count", 10);
        SharedPreferences spLocalCourse = MyApplication.getContext().getSharedPreferences("local_course", 0);
        for (int i = 0; i < course_count; i++) {
            String s = spLocalCourse.getString("bean_course" + i, "");
            String[] ss = s.split("#");
            List<String> list = Arrays.asList(ss);
            BeanCourse beanCourse = new BeanCourse(list.get(0), list.get(1), list.get(2),
                    list.get(3), list.get(4), list.get(5), list.get(6));
            courseList.add(beanCourse);
        }
        return courseList;
    }
}

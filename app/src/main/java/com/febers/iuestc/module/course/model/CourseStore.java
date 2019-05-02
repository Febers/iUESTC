/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-7 下午4:06
 *
 */

package com.febers.iuestc.module.course.model;

import android.content.SharedPreferences;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.entity.BeanCourse;

import com.febers.iuestc.util.FileUtil;
import com.febers.iuestc.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 课表的保存类，保存在sp目录下的"local_course"文件中
 * 每节课以“bean_course”开头， 原始字符串以#分隔
 */
public class CourseStore {

    static void saveToFile(List<BeanCourse> courseList) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FileUtil.appDataDir + "/courses");
            fileWriter.write(new Gson().toJson(courseList));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static List<BeanCourse> getByFile() {
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(FileUtil.appDataDir + "/courses");
            char[] chars = new char[1];
            while (fileReader.read(chars) != -1) {
                builder.append(chars);
            }
            if (builder.toString().isEmpty()) {
                return new ArrayList<>();
            }
            Type type = new TypeToken<List<BeanCourse>>(){}.getType();
            return new Gson().fromJson(builder.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @deprecated 用sp保存，极其浪费内存
     */
    static void save(List<BeanCourse> courseList) {
        //保存课程数目
        SPUtil.getInstance().put("course_count", courseList.size());
        SharedPreferences.Editor editor = MyApp.getContext()
                .getSharedPreferences("local_course", 0).edit();
        editor.clear();
        editor.apply();
        for (int i = 0; i < courseList.size(); i++) {
            editor.putString("bean_course"+i, courseList.get(i).toString());
        }
        editor.apply();
    }

    /**
     * @deprecated 用sp保存，极其浪费内存
     */
    static List<BeanCourse> get() {
        List<BeanCourse> courseList = new ArrayList<>();
        int course_count = SPUtil.getInstance().get("course_count", 10);
        SharedPreferences spLocalCourse = MyApp.getContext().getSharedPreferences("local_course", 0);
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

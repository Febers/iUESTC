package com.febers.iuestc.module.course.model;

import com.febers.iuestc.entity.BeanCourse;

import com.febers.iuestc.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
}

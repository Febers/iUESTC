/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 上午11:22
 *
 */

package com.febers.iuestc.module.exam.model;

import android.content.SharedPreferences;

import com.febers.iuestc.MyApplication;
import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.util.FileUtil;
import com.febers.iuestc.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExamStore {

    static void saveToFile(List<BeanExam> examList, int type) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FileUtil.appDataDir + "/exam" + type);
            fileWriter.write(new Gson().toJson(examList));
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

    static List<BeanExam> getByFile(int type) {
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(FileUtil.appDataDir + "/exam" + type);
            char[] chars = new char[1];
            while (fileReader.read(chars) != -1) {
                builder.append(chars);
            }
            if (builder.toString().isEmpty()) {
                return new ArrayList<>();
            }
            Type type1 = new TypeToken<List<BeanExam>>(){}.getType();
            return new Gson().fromJson(builder.toString(), type1);
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
    static void save(List<BeanExam> examList, int type) {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("exam_"+type,
                0).edit();
        editor.clear();
        editor.apply();
        for (int i = 0; i < examList.size(); i++) {
            BeanExam exam = examList.get(i);
            editor.putString("exam"+i, exam.getNum()+"@"+exam.getName()+"@"+exam.getDate()+"@"+exam.getTime()
                    +"@"+exam.getPosition()+"@"+exam.getSeat()+"@"+exam.getStatus()+"@"+exam.getOther()+"@"+exam.getNoPost());
        }
        editor.putInt("size", examList.size());
        editor.apply();
        SPUtil.getInstance().put("exam_"+type, true);
    }

    /**
     * @deprecated 用sp保存，极其浪费内存
     */
    static List<BeanExam> get(int type) {
        List<BeanExam> list = new ArrayList<>();
        String examName = "exam_1";
        if (type == 2) {
            examName = "exam_2";
        }
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(examName, 0);
        int size = preferences.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            BeanExam exam = new BeanExam();
            String s = preferences.getString("exam"+i, "");
            String[] ss = s.split("@");
            exam.setNum(ss[0]);
            exam.setName(ss[1]);
            exam.setDate(ss[2]);
            exam.setTime(ss[3]);
            exam.setPosition(ss[4]);
            exam.setSeat(ss[5]);
            exam.setStatus(ss[6]);
            exam.setOther(ss[7]);
            exam.setNoPost(Boolean.valueOf(ss[8]));
            list.add(exam);
        }
        return list;
    }
}

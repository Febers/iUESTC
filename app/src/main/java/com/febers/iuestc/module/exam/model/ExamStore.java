/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 上午11:22
 *
 */

package com.febers.iuestc.module.exam.model;

import android.content.SharedPreferences;

import com.febers.iuestc.base.MyApplication;
import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.util.CustomSPUtil;

import java.util.ArrayList;
import java.util.List;

public class ExamStore {
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
        CustomSPUtil.getInstance().put("exam_"+type, true);
    }

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

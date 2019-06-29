package com.febers.iuestc.module.exam.model;

import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.util.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class ExamStore {

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
}

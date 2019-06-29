package com.febers.iuestc.module.grade.model;

import com.febers.iuestc.util.FileUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class GradeStore {

    static void saveSourceCode(String source) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FileUtil.appDataDir + "/grade");
            fileWriter.write(source);
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

    static String getSourceCode() {
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(FileUtil.appDataDir + "/grade");
            char[] chars = new char[1];
            while (fileReader.read(chars) != -1) {
                builder.append(chars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (Exception e) { //如果没有文件，reader为空，直接拦截异常
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}

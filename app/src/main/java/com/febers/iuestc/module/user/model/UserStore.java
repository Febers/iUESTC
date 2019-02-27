package com.febers.iuestc.module.user.model;

import com.febers.iuestc.entity.BeanUser;
import com.febers.iuestc.util.FileUtil;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserStore {

    public static void saveUserToFile(BeanUser user) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FileUtil.appDataDir + "/user");
            fileWriter.write(new Gson().toJson(user));
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

    public static BeanUser getUserByFile() {
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(FileUtil.appDataDir + "/user");
            char[] chars = new char[1];
            while (fileReader.read(chars) != -1) {
                builder.append(chars);
            }
            if (builder.toString().isEmpty()) {
                return new BeanUser();
            }
            return new Gson().fromJson(builder.toString(), BeanUser.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new BeanUser();
        } finally {
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

package com.febers.iuestc.util;

import com.febers.iuestc.net.SingletonClient;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通过获取GitHub仓库上特定文件的内容
 * 得到semesterId，错误的id将使考试安排出错+-
 */
public class SemesterUtil {
    public static String getSemesterId() {
        OkHttpClient client = SingletonClient.getInstance();
        String idUrl = "https://raw.githubusercontent.com/Febers/iUESTC/master/app/src/main/assets/semester_id";
        Request request = new Request.Builder()
                .url(idUrl)
                .get()
                .build();
        try {
            Response idRes = client.newCall(request).execute();
            String id = idRes.body().string();
            if (id == null) {
                return "";
            }
            return id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

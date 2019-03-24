/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-23 上午11:03
 *
 */

package com.febers.iuestc.module.service.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.febers.iuestc.MyApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.service.presenter.SchoolCalendarContact;
import com.febers.iuestc.util.FileUtil;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalModel implements ICalModel {

    private static final String TAG = "CalModel";
    private SchoolCalendarContact.Presenter calPresenter;
    private Context context = MyApplication.getContext();

    public CalModel(SchoolCalendarContact.Presenter presenter) {
        calPresenter = presenter;
    }

    @Override
    public void getCalendar(Boolean isRefresh) throws Exception {
        if (!isRefresh) {
            new Thread(this::getFromFile).start();
        } else {
            new Thread(this::getCalenderImageFromWeb).start();
        }
    }

    /**
     * 通过获取校历网址上的网址获取图片
     */
    private void getCalenderImageFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(ApiUtil.CALENDER_URL)
                    .build();
            Response response = client.newCall(request).execute();
            String webHtml = response.body().string();
            String imgUrl = getImgUrl(webHtml);
            if (imgUrl.isEmpty()) {
                throw new IOException("imgUrl is null");
            }
            request = new Request.Builder()
                    .get()
                    .url(imgUrl)
                    .build();
            response = client.newCall(request).execute();

            byte[] imgBytes = response.body().bytes();
            SPUtil.getInstance()
                    .put(MyApplication.getContext().getString(R.string.sp_get_calender), true);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            BaseEvent<Bitmap> calEvent = new BaseEvent<>(BaseCode.UPDATE, bitmap);
            calPresenter.calendarResult(calEvent);
            saveImageToFile(imgBytes);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            calPresenter.errorResult("网络连接超时");
        } catch (IOException e) {
            e.printStackTrace();
            calPresenter.errorResult("获取校历失败");
        }
    }

    private String getImgUrl(String webHtml) {
        Document document = Jsoup.parse(webHtml);
        Elements elements = document.select("div[class=\"NewText\"]");
        if (elements.size() == 0) {
            return "";
        }
        Elements elements1 = elements.select("img");
        if (elements1.size() == 0) {
            return "";
        }
        String url = elements1.get(0).attr("src");
        return url;
    }

    private void saveImageToFile(byte[] bytes) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FileUtil.appDataDir + "/cal");
            fileWriter.write(Base64.encodeToString(bytes, Base64.DEFAULT));
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

    private void getFromFile() {
        StringBuilder builder = new StringBuilder();
        FileReader fileReader = null;
        Bitmap bitmap = null;
        try {
            fileReader = new FileReader(FileUtil.appDataDir + "/cal");
            char[] chars = new char[1];
            while (fileReader.read(chars) != -1) {
                builder.append(chars);
            }

            byte[] bytes = Base64.decode(builder.toString(), Base64.DEFAULT);
            if (bytes.length <= 0) {
                calPresenter.calendarResult(new BaseEvent<>(BaseCode.ERROR, bitmap));
            }
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            calPresenter.calendarResult(new BaseEvent<>(BaseCode.LOCAL, bitmap));
        } catch (IOException e) {
            e.printStackTrace();
            calPresenter.calendarResult(new BaseEvent<>(BaseCode.ERROR, bitmap));
        } finally {
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @deprecated 浪费内存
     */
    private void saveImage(byte[] bytes) {
        SharedPreferences.Editor editor = context.getSharedPreferences("calender_bitmap", 0).edit();
        editor.putString("bitmap", Base64.encodeToString(bytes, Base64.DEFAULT));
        editor.apply();
    }

    /**
     * @deprecated 浪费内存
     */
    private void loadLocalImage() {
        SharedPreferences preferences = context.getSharedPreferences("calender_bitmap", 0);
        String s = preferences.getString("bitmap","");
        byte[] bytes = Base64.decode(s, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        BaseEvent<Bitmap> calEvent = new BaseEvent<>(BaseCode.LOCAL, bitmap);
        calPresenter.calendarResult(calEvent);
    }
}

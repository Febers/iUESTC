/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.mvp.presenter.SchoolCalendarContact;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalModel implements ICalModel {

    private static final String TAG = "CalModel";
    private SchoolCalendarContact.Presenter calPresenter;
    private Context context = BaseApplication.getContext();
    public CalModel(SchoolCalendarContact.Presenter presenter) {
        calPresenter = presenter;
    }

    @Override
    public void getCalendar(Boolean isRefresh) throws Exception {
        if (!isRefresh) {
            loadLocalImage();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCalenderImg();
            }
        }).start();
    }

    /**
     * 通过获取校历网址上的网址获取图片
     */
    private void getCalenderImg() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(ApiUtil.CALENDER_URL)
                    .build();
            Response response = client.newCall(request).execute();
            String webHtml = response.body().string();
            String imgUrl = getImgUrl(webHtml);
            if (imgUrl == "") {
                throw new IOException("imgUrl is null");
            }
            request = new Request.Builder()
                    .get()
                    .url(imgUrl)
                    .build();
            response = client.newCall(request).execute();

            byte[] imgBytes = response.body().bytes();

            saveImage(imgBytes);
            CustomSharedPreferences.getInstance()
                    .put(BaseApplication.getContext().getString(R.string.sp_get_calender), true);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            calPresenter.calendarResult(bitmap);
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

    private void saveImage(byte[] bytes) {
        SharedPreferences.Editor editor = context.getSharedPreferences("calender_bitmap", 0).edit();
        editor.putString("bitmap", Base64.encodeToString(bytes, Base64.DEFAULT));
        editor.apply();
    }

    private void loadLocalImage() {
        SharedPreferences preferences = context.getSharedPreferences("calender_bitmap", 0);
        String s = preferences.getString("bitmap","");
        byte[] bytes = Base64.decode(s, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        calPresenter.calendarResult(bitmap);
    }
}

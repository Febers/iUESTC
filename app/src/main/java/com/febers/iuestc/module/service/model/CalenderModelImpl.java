package com.febers.iuestc.module.service.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.service.presenter.CalenderContract;
import com.febers.iuestc.util.FileUtil;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.febers.iuestc.base.Constants.CALENDER_GOT;

public class CalenderModelImpl implements CalenderContract.Model {

    private static final String TAG = "CalenderModelImpl";

    private CalenderContract.Presenter calPresenter;

    public CalenderModelImpl(CalenderContract.Presenter presenter) {
        calPresenter = presenter;
    }

    @Override
    public void calendarService(Boolean isRefresh) throws Exception {
        if (!isRefresh) {
            new Thread(this::getSavedCalender).start();
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

            InputStream imageStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            BaseEvent<Bitmap> calEvent = new BaseEvent<>(BaseCode.UPDATE, bitmap);
            calPresenter.calendarResult(calEvent);

            saveCalender(bitmap);
            SPUtil.INSTANCE().put(CALENDER_GOT, true);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            calPresenter.errorResult("网络连接超时");
        } catch (Exception e) {
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

    private void  saveCalender(Bitmap bitmap) {
        File file = new File(FileUtil.appDataDir + "/cal.png");
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    return;
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSavedCalender() {
        try {
            File file = new File(FileUtil.appDataDir + "/cal.png");
            if (!file.exists()) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeFile(FileUtil.appDataDir + "/cal.png");
            calPresenter.calendarResult(new BaseEvent<>(BaseCode.LOCAL, bitmap));
        } catch (Exception e) {
            calPresenter.calendarResult(new BaseEvent<>(BaseCode.ERROR, null));
        }
    }
}

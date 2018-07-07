/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-15 下午4:13
 *
 */

package com.febers.iuestc.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.entity.BeanBookPosition;
import com.febers.iuestc.mvp.presenter.LibraryContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.RandomUtil;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LibraryModel implements ILibraryModel {

    private static final String TAG = "LibraryModel";
    private LibraryContract.Presenter libraryPresenter;
    private OkHttpClient mClient;
    private List<BeanBook> bookList = new ArrayList<>();
    private String result = "";
    private String nextPageUrl = "";
    private Context context = BaseApplication.getContext();

    public LibraryModel(LibraryContract.Presenter presenter) {
        libraryPresenter = presenter;
    }

    /**
     * 获取借阅信息，先登录
     */
    @Override
    public void getReadHistory(final Boolean isRefresh, final int page) {
        new Thread( () -> {
            if (page == 1 && (!isRefresh)) {
                if (loadLoaclHistory()){
                    return;
                }
            }
            String stId = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_user_id), "");
            String stPw = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_user_pw), "");
            OkHttpClient client = SingletonClient.getInstance();
            FormBody formLogin = new FormBody.Builder()
                    .add("extpatid", stId)
                    .add("extpatpw", stPw)
                    .add("code", "")
                    .add("pin", "")
                    .add("submit.x", RandomUtil.getRandomFrom0(100)+"")
                    .add("submit.y", RandomUtil.getRandomFrom0(100)+"")
                    .add("submit", "submit")
                    .build();
            Request request = new Request.Builder()
                    .url("https://webpac.uestc.edu.cn/patroninfo*chx")
                    .post(formLogin)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                //验证登录
                Document document = Jsoup.parse(result);
                Elements els = document.select("span[class=\"loggedInMessage\"]");
                for (Element e:els) {
                    if (e.text().contains("已登入")) {
                        Log.d(TAG, "getReadHistory: 登录成功");
                        break;
                    } else {
                        libraryPresenter.errorResult("图书馆登录出错");
                        return;
                    }
                }
                //获取历史记录链接
                String historyUrl = "";
                Elements elements = document.select("a[href][target]");
                for (int i = 0; i < elements.size(); i++) {
                    if(elements.get(i).attr("href").contains("history")) {
                        historyUrl = elements.get(i).attr("href");
                    }
                }
                request = new Request.Builder()
                        .url("https://webpac.uestc.edu.cn"+historyUrl+"&page="+page)
                        .build();
                response = client.newCall(request).execute();
                result = response.body().string();
                resolveHistory(result);

            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                libraryPresenter.errorResult("请求超时,可能需要校园网");
                return;
            } catch (IOException e) {
                e.printStackTrace();
                libraryPresenter.errorResult("获取历史记录失败");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                libraryPresenter.errorResult("图书馆登录出现异常");
                return;
            }
        }).start();
    }

    private void resolveHistory(String sourceCode) {
        Document document = Jsoup.parse(sourceCode);
        Elements elsTitle = document.select("span[class=\"patFuncTitleMain\"]");
        Elements elsAuthor = document.select("td[class=\"patFuncAuthor\"]");
        Elements elsDate = document.select("td[class=\"patFuncDate\"]");
        for (int i = 0; i < elsTitle.size(); i++) {
            BeanBook book = new BeanBook();
            book.setTitle(elsTitle.get(i).text());
            book.setAuthor(elsAuthor.get(i).text());
            book.setDate(elsDate.get(i).text());
            bookList.add(book);
        }
        if (bookList.size() != 0) {
            SharedPreferences preferences = context.getSharedPreferences("book_history", 0);
            SharedPreferences.Editor editor = preferences.edit();
            int size = preferences.getInt("size", 0);
            int i = size;
            for (; i < bookList.size(); i++) {
                editor.putString("book"+i, bookList.get(i).getTitle() + "@" +bookList.get(i).getDate());
            }
            editor.putInt("size", i);
            editor.commit();
        }
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_library_history), true);
        libraryPresenter.historyResult(bookList);
    }

    /**
     * X(关键字) t(题名) a(作者)
     * charg 关键词
     * shscope 校区 1(全部), 2(沙河), 3(清水河)
     * sortdropdown -(相关度)
     * SORT 排序依据 AXZ(题名) D(相关度)
     */
    @Override
    public void queryBook(final String keyword, final String  type, final String  postion,
                          int status, final int page, final String nextPage) throws Exception{
        mClient = SingletonClient.getInstance();
        new Thread( () -> {
            String url = nextPage;
            if (!nextPage.equals("null")) {
                url = "https://webpac.uestc.edu.cn" + nextPage;
            } else {
                url = "https://webpac.uestc.edu.cn/search~S3*chx/?searchtype=X" +
                        "&searcharg=" + keyword +
                        "&searchscope=" + postion +
                        "&sortdropdown=t" +
                        "&SORT=DZ" +
                        "&extended=0" +
                        "&SUBMIT=%E6%A3%80%E7%B4%A2" +
                        "&searchlimits=" +
                        "&searchorigarg=" + type + keyword;
            }
            Log.d(TAG, "url: " + url);

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try {
                Response response = mClient.newCall(request).execute();
                result = response.body().string();
            } catch (SocketTimeoutException e) {
                libraryPresenter.errorResult("请求超时,可能需要校园网");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                libraryPresenter.errorResult("搜索出错，请联系开发者");
            }

            Document document = Jsoup.parse(result);
            Elements elements = document.select("td.briefCitRow");
            for (Element e:elements) {
                BeanBook book = new BeanBook();
                book.setTitle(e.select("span.briefcitTitle").text());
                try{
                    book.setPosition(e.select("[width=27%]").get(1).text());
                    book.setGetBookCode(e.select("[width=20%]").get(1).text());
                    book.setStatus(e.select("[width=15%]").get(1).text());
                    book.setBookID(e.select("[width=30%]").get(1).text());
                    book.setDetailPosition(getPosition(book.getBookID()));
                    bookList.add(book);
                }catch (IndexOutOfBoundsException ie) {
                    continue;
                }
            }

            String next = page+1+"";
            Elements elsPage = document.select("a");
            for (Element element:elsPage) {
                if (element.text().equals(next)) {
                    nextPageUrl = element.attr("href");
                    break;
                }
            }
            libraryPresenter.queryResult("成功", nextPageUrl, bookList);
        }).start();
    }

    private String  getPosition(String bookID) {
        String url = "http://222.197.165.95:81/RFIDTest/WebServiceByBookID.asmx/" +
                "GetBookCaseTransByBookID?jsoncallback=jsonp1524200829325&_=1524200829334&szBookID=" + bookID;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            int s = result.indexOf("{");
            result = result.substring(s, result.length()-1);
            Gson gson = new Gson();
            BeanBookPosition bookPosition = gson.fromJson(result, BeanBookPosition.class);
            return bookPosition.getResponseData().replace(":", "");
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "查询超时";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Boolean loadLoaclHistory() {
        SharedPreferences preferences = BaseApplication.getContext().getSharedPreferences("book_history", 0);
        int size = preferences.getInt("size", 0);
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            BeanBook book = new BeanBook();
            String bookTiAndDa = preferences.getString("book"+i, "");
            String[] stringArray = bookTiAndDa.split("@");
            book.setTitle(stringArray[0]);
            book.setDate(stringArray[1]);
            bookList.add(book);
        }
        libraryPresenter.historyResult(bookList);
        return true;
    }

}

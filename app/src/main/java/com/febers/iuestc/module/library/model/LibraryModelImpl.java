/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.library.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.febers.iuestc.base.MyApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.presenter.LibraryContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.CustomSPUtil;
import com.febers.iuestc.util.RandomUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LibraryModelImpl implements LibraryContract.Model {

    private static final String TAG = "LibraryModelImpl";
    private LibraryContract.Presenter libraryPresenter;
    private OkHttpClient mClient;
    private List<BeanBook> bookList = new ArrayList<>();

    public LibraryModelImpl(LibraryContract.Presenter presenter) {
        libraryPresenter = presenter;
    }

    /**
     * 获取借阅信息，先登录
     */
    @Override
    public void readHistoryService(final Boolean isRefresh, final int page) {
        new Thread( () -> {
            if (page == 1 && (!isRefresh)) {
                if (loadLocalHistory()){
                    return;
                }
            }
//            String stId = CustomSPUtil.getInstance().get(context.getString(R.string.sp_user_id), "");
//            String stPw = CustomSPUtil.getInstance().get(context.getString(R.string.sp_user_pw), "");
            OkHttpClient client = SingletonClient.getInstance();
            FormBody formLogin = new FormBody.Builder()
//                    .add("extpatid", stId)
//                    .add("extpatpw", stPw)
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
                        Log.d(TAG, "readHistoryService: 登录成功");
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
            SharedPreferences preferences = MyApplication.getContext()
                    .getSharedPreferences("book_history", 0);
            SharedPreferences.Editor editor = preferences.edit();
            int size = preferences.getInt("size", 0);
            int i = size;
            for (; i < bookList.size(); i++) {
                editor.putString("book"+i, bookList.get(i).getTitle() + "@" +bookList.get(i).getDate());
            }
            editor.putInt("size", i);
            editor.commit();
        }
        CustomSPUtil.getInstance().put(MyApplication.getContext()
                .getString(R.string.sp_library_history), true);
        libraryPresenter.historyResult(bookList);
    }

    @Override
    public void queryBookService(final String keyword, final int type, final int page) throws Exception{
        mClient = SingletonClient.getInstance();
        new Thread( () -> {
            String url = "http://222.197.165.97:8080/search?" +
                        "kw=" + keyword + "&" +
                        "searchtype=" + queryTypeChange(type) + "&" +
                        "page=" + page + "&" +
                        "xc=6";
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try {
                Response response = mClient.newCall(request).execute();
                String result = response.body().string();
                resolveQueryHtml(page, result);
            } catch (SocketTimeoutException e) {
                libraryPresenter.errorResult("请求超时");
                libraryPresenter.queryResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            } catch (IOException e) {
                e.printStackTrace();
                libraryPresenter.errorResult("搜索出错");
                libraryPresenter.queryResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            }
        }).start();
    }

    private void resolveQueryHtml(int page, String html) {
        if (html.contains("暂无馆藏") || (!isInQuery(page, html))) {
            libraryPresenter.queryResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            return;
        }
        List<BeanBook> bookList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("li");
        for (int i = 0; i < elements.size(); i++) {
            BeanBook book = new BeanBook();
            String code = elements.get(i).select("font[color=\"black\"]").text();
            book.setUrl("http://222.197.165.97:8080"+elements.get(i).select("a[href]").attr("href"));
            book.setName(elements.get(i).select("a[class=\"title\"]").text());
            book.setInfo(elements.get(i).text()
                    .replace(code, "")
                    .replace(book.getName(), "")
                    .replace("   ", ""));
            bookList.add(book);
        }
        BaseEvent<List<BeanBook>> event = new BaseEvent<>(BaseCode.UPDATE, bookList);
        libraryPresenter.queryResult(event);
    }

    @Override
    public void bookDetailService(String url) {
        mClient = SingletonClient.getInstance();
        new Thread( () -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try {
                Response response = mClient.newCall(request).execute();
                String result = response.body().string();
                resolveBookDetail(result);
            } catch (SocketTimeoutException e) {
                libraryPresenter.errorResult("请求超时");
            } catch (IOException e) {
                e.printStackTrace();
                libraryPresenter.errorResult("获取图书详情出错");
            }
        }).start();
    }


    private void resolveBookDetail(String html) {
        try {
            StringBuilder builder = new StringBuilder();
            int startHeader = html.indexOf("<div class=\"header\">");
            int endHeader = html.indexOf("</div>", startHeader);
            int startBottom = html.indexOf("<div class=\"bottom\">");
            int endBottom = html.lastIndexOf("</div>");
            builder.append(html.substring(0, startHeader+1));
            builder.append(html.substring(endHeader+1, startBottom));
            libraryPresenter.bookDetailResult(new BaseEvent<>(BaseCode.UPDATE, builder.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            libraryPresenter.bookDetailResult(new BaseEvent<>(BaseCode.UPDATE, html));
        }
    }

    private Boolean loadLocalHistory() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("book_history", 0);
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

    private String  queryTypeChange(int type) {
        if (type == 0) {
            return "X";  //关键字
        }
        if (type == 1) {
            return "a";  //作者
        }
        if (type == 2) {
            return "t";  //题名
        }
        if (type == 3) {
            return "d";  //主题
        }
        if (type == 4) {
            return "i";  //ISBN
        }
        return "X";
    }

    private Boolean isInQuery(int page, String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("td[align=\"center\"]");
        int startPage = elements.text().indexOf("共");
        int endPage = elements.text().indexOf("页");
        try {
            int pages = Integer.parseInt(elements.text().substring(startPage+1, endPage));
            return page <= pages;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午11:00
 *
 */

package com.febers.iuestc.module.news.model;

import android.util.Log;

import com.febers.iuestc.entity.BeanNews;
import com.febers.iuestc.module.news.presenter.NewsContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsModel implements INewsModel {

    private static final String TAG = "NewsModel";
    private NewsContract.Presenter newsPresenter;
    private List<BeanNews> newsList = Collections.synchronizedList(new ArrayList<>());  //解决多线程中list add方法的数组越界问题
    private int tmp = 0;   //这是一个标志位，记录是否已获取全部新闻

    public NewsModel(NewsContract.Presenter newsPresenter) {
        this.newsPresenter = newsPresenter;
    }

    @Override
    public void newsService(Boolean isRefresh, int type, int position) {
        new Thread(() ->
            getNewsHtml(type, position)
        ).start();
    }

    private void getNewsHtml(int type, int position) {
        OkHttpClient client = SingletonClient.getInstance();
        String url = ApiUtil.getNewsUrl(type, position);
        if (url == null) {
            newsPresenter.errorResult("获取公告失败");
            return;
        }

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String sourceCode = response.body().string();
            Document document = Jsoup.parse(sourceCode);
            //本科生
            if (type == 0) {
                Elements es = document.select("div[class=\"wrap\"]");
                Element element = es.first();
                Elements elements = element.select("a[href=\"#\"]");
                Elements esDate = element.select("i");
                for (int i=0; i<elements.size(); i++) {
                    Element e = elements.get(i);
                    String title = e.attr("title");
                    String date = esDate.get(i).text();
                    String id = e.attr("newsId");
                    BeanNews beanNews = new BeanNews();
                    beanNews.setTitle(title);
                    beanNews.setDate(date);
                    beanNews.setNewsId("http://www.jwc.uestc.edu.cn/web/News!view.action?id=" + id);
                    getPerNews(0, id, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            if (Cal(elements.size())) {
                                newsPresenter.newsResult(newsList);
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String text = response.body().string();
                            beanNews.setText(suitText(0, text));
                            newsList.add(beanNews);
                            if (Cal(elements.size())) {
                                Collections.sort(newsList);
                                newsPresenter.newsResult(newsList);
                            }
                        }
                    });
                }
            } else if (type == 1) {
                //研究生
                Elements els = document.select("div[class=\"cf topic_items\"]");
                Element el = els.first();
                Elements elements = el.select("a[href]");   //url和标题
                Elements elsDate = el.select("div[class=\"time\"]");    //时间

                for (int i = 0; i < elements.size(); i++) {
                    String title = elements.get(i).text();
                    String newsId = elements.get(i).attr("href");
                    String date = elsDate.get(i).text();
                    BeanNews beanNews = new BeanNews();
                    beanNews.setTitle(title);
                    beanNews.setDate(date);
                    beanNews.setNewsId("http://gr.uestc.edu.cn"+newsId);
                    getPerNews(1, newsId, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            if (Cal(elements.size())) {
                                newsPresenter.newsResult(newsList);
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String text = response.body().string();
                            beanNews.setText(suitText(1, text));
                            newsList.add(beanNews);
                            if (Cal(elements.size())) {
                                Collections.sort(newsList);
                                newsPresenter.newsResult(newsList);
                            }
                        }
                    });
                }
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            newsPresenter.errorResult("获取超时，请检查网络");
        } catch (NullPointerException e) {
            e.printStackTrace();
            newsPresenter.errorResult("获取公告失败");
        } catch(IOException e) {
            e.printStackTrace();
            newsPresenter.errorResult("获取公告失败");
        } catch (Exception e) {
            newsPresenter.errorResult("获取公告失败");
        }
    }

    /**
     * 通过异步的形式以提高效率
     * 获取单独的新闻
     * @param newsId 新闻id
     * @param callback 回调
     */
    private void getPerNews(int type, String newsId, Callback callback) throws Exception{
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        String url = "";
        if (type == 0) {
            url = "http://www.jwc.uestc.edu.cn/web/News!view.action?id=" + newsId;
        } else if (type == 1) {
            url = "http://gr.uestc.edu.cn"+newsId;
        }
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 计算获取新闻的数量
     * @param all 全部新闻数
     * @return 当前已获取新闻总数与新闻总数的比较
     */
    private Boolean Cal(int all) {
        //说明有一次请求
        tmp++;
        return tmp == all;
    }

    /**
     * 在原html内添加适应移动端的meta
     * 并删除多余的text
     * @param sourceText 原网页
     * @return
     */
    private String suitText(int type, String sourceText) {
        String result = sourceText;
        String suitMeta = "<meta charset=\"utf-8\">"+
                "\n"+
                "<meta name=\"viewport\" content=\"width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\">\n";
        try {
            if (type == 0) {
                int startText = result.indexOf("<div class=\"contentNewText\">");
                int endText = result.indexOf("<div class=\"prevNextText clearfix\">");

                int endStyle = result.indexOf("</span></strong></p>", startText);
                //个别新闻没有上面这个标签
                if (endStyle == -1) {
                    endStyle = startText;
                }
                result = result.substring(endStyle, endText);
            } else if (type == 1) {
                int start = result.indexOf("<div class=\"content\">");
                int end = result.indexOf("<div class=\"prev_next\">");
                result = result.substring(start, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "suitText: 剪切新闻原网页时出现异常");
        }
        result = result.replaceAll("href", "");
        result = suitMeta + result;
        return result;
    }
}

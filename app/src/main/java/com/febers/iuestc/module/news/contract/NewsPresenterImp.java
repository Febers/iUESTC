/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午9:15
 *
 */

package com.febers.iuestc.module.news.contract;

import android.util.Log;

import com.febers.iuestc.module.news.model.BeanNews;
import com.febers.iuestc.module.news.model.INewsModel;
import com.febers.iuestc.module.news.model.NewsModel;

import java.util.List;

public class NewsPresenterImp extends NewsContract.Presenter {

    private static final String TAG = "NewsPresenterImp";
    private INewsModel newsModel = new NewsModel(this);
    private int type;
    private int position;

    public NewsPresenterImp(NewsContract.View view, int type, int position) {
        super(view);
        this.type = type;
        this.position = position;
        Log.d(TAG, "NewsPresenterImp: " + position);
    }

    @Override
    public void newsRequest(Boolean isRefresh) {
        try {
            newsModel.newsService(isRefresh, type, position);
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError("获取新闻出错");
        }
    }

    @Override
    public void newsResult(List<BeanNews> newsList) {
        if (mView == null) {
            return;
        }
        mView.showNews(newsList);
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.news.presenter;

import android.util.Log;

import com.febers.iuestc.entity.BeanNews;
import com.febers.iuestc.module.news.model.NewsModel;

import java.util.List;

public class NewsPresenterImpl extends NewsContract.Presenter {

    private static final String TAG = "NewsPresenterImpl";

    private int type;
    private int position;

    public NewsPresenterImpl(NewsContract.View view, int type, int position) {
        super(view);
        this.type = type;
        this.position = position;
        Log.d(TAG, "NewsPresenterImpl: " + position);
    }

    @Override
    public void newsRequest(Boolean isRefresh) {
        NewsContract.Model newsModel = new NewsModel(this);
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

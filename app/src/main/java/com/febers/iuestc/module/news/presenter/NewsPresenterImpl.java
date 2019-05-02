package com.febers.iuestc.module.news.presenter;

import com.febers.iuestc.entity.BeanNews;
import com.febers.iuestc.module.news.model.NewsModelImpl;

import java.util.List;

public class NewsPresenterImpl extends NewsContract.Presenter {

    private static final String TAG = "NewsPresenterImpl";

    private int type;
    private int position;

    public NewsPresenterImpl(NewsContract.View view, int type, int position) {
        super(view);
        this.type = type;
        this.position = position;
    }

    @Override
    public void newsRequest(Boolean isRefresh) {
        NewsContract.Model newsModel = new NewsModelImpl(this);
        try {
            newsModel.newsService(isRefresh, type, position);
        } catch (Exception e) {
            e.printStackTrace();
            view.onError("获取新闻出错");
        }
    }

    @Override
    public void newsResult(List<BeanNews> newsList) {
        if (view == null) {
            return;
        }
        view.showNews(newsList);
    }
}

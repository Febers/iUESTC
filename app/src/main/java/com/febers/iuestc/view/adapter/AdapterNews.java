package com.febers.iuestc.view.adapter;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanNews;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

public class AdapterNews extends CommonBaseAdapter<BeanNews> {

    public AdapterNews(Context context, List<BeanNews> datas) {
        this(context, datas, false);
    }

    public AdapterNews(Context context, List<BeanNews> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BeanNews beanNews, int i) {
        viewHolder.setText(R.id.tv_news_title, beanNews.getTitle());
        viewHolder.setText(R.id.tv_news_date, beanNews.getDate());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_news;
    }
}

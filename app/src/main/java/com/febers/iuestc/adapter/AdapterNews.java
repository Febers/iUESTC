/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.modules.news.model.BeanNews;
import com.febers.iuestc.modules.news.view.NewsDetailActivity;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {

    private static final String TAG = "AdapterNews";
    private Context context;
    private List<BeanNews> newsList;

    public AdapterNews(List<BeanNews> newsList) {
        this.newsList = newsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewsTitle;
        TextView tvNewsDate;
        CardView cvNews;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNewsTitle = itemView.findViewById(R.id.tv_news_title);
            tvNewsDate = itemView.findViewById(R.id.tv_news_date);
            cvNews = itemView.findViewById(R.id.cv_news);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        AdapterNews.ViewHolder viewHolder = new AdapterNews.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanNews news = newsList.get(position);
        holder.tvNewsTitle.setText(news.getTitle());
        holder.tvNewsDate.setText(news.getDate());
        holder.cvNews.setOnClickListener(v-> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("text", news.getText());
            intent.putExtra("title", news.getTitle());
            intent.putExtra("url", news.getNewsId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

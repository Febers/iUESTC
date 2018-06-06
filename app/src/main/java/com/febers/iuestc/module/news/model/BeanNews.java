package com.febers.iuestc.module.news.model;

import android.support.annotation.NonNull;

public class BeanNews implements Comparable<BeanNews>{
    private String title;
    private String newsId;
    private String text;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull BeanNews o) {
        return o.getDate().hashCode() - getDate().hashCode();
    }
}

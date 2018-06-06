package com.febers.iuestc.module.news.model;

public interface INewsModel {
    void newsService(Boolean isRefresh, int type, int position) throws Exception;
}

package com.febers.iuestc.module.library.model;

/**
 * Created by Febers on img_2018/2/3.
 */

public interface ILibraryModel {
    void getReadHistory(Boolean isRefresh, int page) throws Exception;
    void queryBook(String keyword, String  type, String postion, int status, int page, String nextPage) throws Exception;
}

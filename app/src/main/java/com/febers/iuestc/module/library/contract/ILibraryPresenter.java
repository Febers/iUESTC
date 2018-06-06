package com.febers.iuestc.module.library.contract;

import com.febers.iuestc.module.library.model.BeanBook;

import java.util.List;

/**
 * Created by Febers on img_2018/2/3.
 */

public interface ILibraryPresenter {
    void queryRequest(String keyword, String type, String postion, int status, int page, String nextPage);
    void queryResult(String status, String nextPageUrl, List<BeanBook> bookList);
    void historyRequest(Boolean isRefresh, int page);
    void historyResult(List<BeanBook> list);
    void showErrorMsg(String msg);
}

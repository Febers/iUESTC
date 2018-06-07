/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:49
 *
 */

package com.febers.iuestc.module.library.contract;

import com.febers.iuestc.module.library.model.BeanBook;
import com.febers.iuestc.module.library.model.ILibraryModel;
import com.febers.iuestc.module.library.model.LibraryModel;

import java.util.List;

public class LibraryPresenterImp extends LibraryContract.Presenter {

    private static final String TAG = "LibraryPresenterImp";

    private ILibraryModel libraryModel = new LibraryModel(this);

    public LibraryPresenterImp(LibraryContract.View view) {
        super(view);
    }

    @Override
    public void queryRequest(String keyword, String type, String postion, int status, int page, String nextPage) {
        try {
            libraryModel.queryBook(keyword, type, postion, status, page, nextPage);
        } catch (Exception e) {
            e.printStackTrace();
            if (mView != null) {
                mView.onError("查询图书出现异常");
            }
        }
    }

    @Override
    public void queryResult(String status, String nextPageUrl, List<BeanBook> bookList) {
        if (mView != null) {
            mView.showQuery(status, nextPageUrl, bookList);
        }
    }

    @Override
    public void historyRequest(Boolean isRefresh, int page) {
        try {
            libraryModel.getReadHistory(isRefresh, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void historyResult(List<BeanBook> list) {
        if (mView != null) {
            mView.showHistory(list);
        }
    }
}

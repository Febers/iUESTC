/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.modules.library.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.modules.library.model.BeanBook;

import java.util.List;

public interface LibraryContract {

    interface View extends BaseView {
        void showQuery(String status, String nextPageUrl, List<BeanBook> bookList);
        void showHistory(List<BeanBook> list);
    }

    abstract class Presenter extends BasePresenter<LibraryContract.View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void queryRequest(String keyword, String type, String postion, int status, int page, String nextPage);
        public abstract void queryResult(String status, String nextPageUrl, List<BeanBook> bookList);
        public abstract void historyRequest(Boolean isRefresh, int page);
        public abstract void historyResult(List<BeanBook> list);
    }
}

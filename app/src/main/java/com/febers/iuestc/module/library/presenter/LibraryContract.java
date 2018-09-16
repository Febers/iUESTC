/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.library.presenter;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.entity.BeanBook;

import java.util.List;

public interface LibraryContract {

    interface Model {
        void readHistoryService(Boolean isRefresh, int page) throws Exception;
        void queryBookService(String keyword, int type,int page) throws Exception;
        void bookDetailService(String url) throws Exception;
    }

    interface View extends BaseView {
        void showQuery(BaseEvent<List<BeanBook>> event);
        void showBookDetail(BaseEvent<String> event);
    }

    abstract class Presenter extends BasePresenter<LibraryContract.View> {
        public Presenter(View view) {
            super(view);
        }
        public abstract void queryRequest(String keyword, int type, int page);
        public abstract void queryResult(BaseEvent<List<BeanBook>> event);
        public abstract void historyRequest(Boolean isRefresh, int page);
        public abstract void historyResult(List<BeanBook> list);
        public abstract void bookDetailRequest(String url);
        public abstract void bookDetailResult(BaseEvent<String> event);
    }
}

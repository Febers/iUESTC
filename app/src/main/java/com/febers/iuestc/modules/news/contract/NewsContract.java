/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午9:13
 *
 */

package com.febers.iuestc.modules.news.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.modules.news.model.BeanNews;

import java.util.List;

public interface NewsContract {

    interface View extends BaseView {
        void showNews(List<BeanNews> newsList);
    }

    abstract class Presenter extends BasePresenter<NewsContract.View> {

        public Presenter(View view) {
            super(view);

    }
        public abstract void newsRequest(Boolean isRefresh);

        public abstract void newsResult(List<BeanNews> newsList);
    }

}

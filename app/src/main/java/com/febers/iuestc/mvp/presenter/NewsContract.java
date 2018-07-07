/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.presenter;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.entity.BeanNews;

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

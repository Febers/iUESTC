package com.febers.iuestc.module.news.contract;

import com.febers.iuestc.base.BasePresenter;
import com.febers.iuestc.base.BaseView;
import com.febers.iuestc.module.news.model.BeanNews;

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

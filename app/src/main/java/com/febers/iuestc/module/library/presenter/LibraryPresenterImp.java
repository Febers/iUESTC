package com.febers.iuestc.module.library.presenter;

import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.model.LibraryModelImpl;

import java.util.ArrayList;
import java.util.List;

public class LibraryPresenterImp extends LibraryContract.Presenter {

    private static final String TAG = "LibraryPresenterImp";

    private LibraryContract.Model libraryModel = new LibraryModelImpl(this);

    public LibraryPresenterImp(LibraryContract.View view) {
        super(view);
    }

    @Override
    public void queryRequest(String keyword, int type, int page) {
        try {
            libraryModel.queryBookService(keyword, type, page);
        } catch (Exception e) {
            e.printStackTrace();
            if (view != null) {
                view.onError("查询图书出现异常");
                queryResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            }
        }
    }

    @Override
    public void queryResult(BaseEvent<List<BeanBook>> event) {
        if (view != null) {
            view.showQuery(event);
        }
    }

    @Override
    public void historyRequest(Boolean isRefresh, int page) {
        try {
            libraryModel.readHistoryService(isRefresh, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void historyResult(List<BeanBook> list) {
        if (view != null) {
        }
    }

    @Override
    public void bookDetailRequest(String url) {
        try {
            libraryModel.bookDetailService(url);
        } catch (Exception e) {
            e.printStackTrace();
            view.onError("获取图书详情失败");
        }
    }

    @Override
    public void bookDetailResult(BaseEvent<String> event) {
        if (view != null) {
            view.showBookDetail(event);
        }
    }
}

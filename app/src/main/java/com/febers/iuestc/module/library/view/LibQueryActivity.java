package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.util.ToastUtil;
import com.febers.iuestc.view.adapter.AdapterQuery;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.module.library.presenter.LibraryContract;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.presenter.LibraryPresenterImp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LibQueryActivity extends BaseSwipeActivity implements LibraryContract.View {

    private static final String TAG = "LibQueryActivity";
    private List<BeanBook> bookList = new ArrayList<>();
    private LibraryContract.Presenter libraryPresenter;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView rvLibQuery;
    private AdapterQuery adapterQuery;
    private ImageView imgNull;
    private String keyword;
    private int page=1;
    private int type;

    @Override
    protected int setView() {
        return R.layout.activity_lib_query;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_lib_query;
    }

    @Override
    protected String setToolbarTitle() {
        return "图书检索";
    }

    @Override
    protected void findViewById() {
        rvLibQuery = findViewById(R.id.rv_lib_query);
        smartRefreshLayout = findViewById(R.id.srl_query_book);
        imgNull = findViewById(R.id.iv_null_lib_query);
    }

    @Override
    protected void initView() {
        libraryPresenter = new LibraryPresenterImp(this);

        keyword = getIntent().getStringExtra("keyword");
        type = getIntent().getIntExtra("type", 0);

        rvLibQuery.setLayoutManager(new LinearLayoutManager(this));
        rvLibQuery.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterQuery = new AdapterQuery(this, bookList);
        rvLibQuery.setAdapter(adapterQuery);
        adapterQuery.setOnItemClickListener((viewHolder, beanBook, i) -> {
            Intent intent = new Intent(LibQueryActivity.this, LibDetailActivity.class);
            intent.putExtra("url", beanBook.getUrl());
            startActivity(intent);
        });
        smartRefreshLayout.setEnableRefresh(false);
        if (bookList.size() == 0) {
            smartRefreshLayout.setEnableLoadMore(false);
        }
        smartRefreshLayout.setOnLoadMoreListener( (RefreshLayout refreshLayout) ->{
            page = bookList.size() / 12 + 1;
            sendQueryRequest(true, keyword, type, page);
        });
        sendQueryRequest(false, keyword, type, page);
    }

    @Override
    public void showQuery(BaseEvent<List<BeanBook>> event) {
        dismissProgressDialog();
        smartRefreshLayout.finishLoadMore();
        bookList.addAll(event.getDate());

        if (!(event.getDate().size()<12)) {
            smartRefreshLayout.setEnableLoadMore(true);
        }
        runOnUiThread( () -> {
            if (page == 1 && event.getCode() == BaseCode.ERROR) {
                imgNull.setVisibility(View.VISIBLE);
                return;
            }
            if (page!=1) {
                adapterQuery.notifyDataSetChanged();
                return;
            }
            adapterQuery.setNewData(event.getDate());
        });
    }

    private void sendQueryRequest(Boolean isLoadMore, String keyword, int type, int page) {
        if (!MyApp.checkNetConnecting()) {
            ToastUtil.showShortToast("当前网络不可用");
            return;
        }
        if (!isLoadMore) {
            showProgressDialog();
        }
        libraryPresenter.queryRequest(keyword, type, page);
    }

    @Override
    public void showBookDetail(BaseEvent<String> event) {
    }
}

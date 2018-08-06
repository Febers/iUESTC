/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-7 下午4:53
 *
 */

package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.home.view.HomeActivity;
import com.febers.iuestc.adapter.AdapterHistory;
import com.febers.iuestc.adapter.AdapterQuery;
import com.febers.iuestc.module.library.presenter.LibraryContract;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.presenter.LibraryPresenterImp;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.view.custom.CustomProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class LibQueryActivity extends BaseActivity implements LibraryContract.View{

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
    protected void initView() {
        libraryPresenter = new LibraryPresenterImp(this);

        keyword = getIntent().getStringExtra("keyword");
        type = getIntent().getIntExtra("type", 0);

        Toolbar toolbar = findViewById(R.id.tb_lib_query);
        toolbar.setTitle("图书检索");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener( (View v) -> {
            Intent intent = new Intent(LibQueryActivity.this, HomeActivity.class);
            intent.putExtra("lib_activity", true);
            startActivity(intent);
            finish();
        });
        rvLibQuery = findViewById(R.id.rv_lib_query);
        LinearLayoutManager llmQueryBook = new LinearLayoutManager(this);
        rvLibQuery.setLayoutManager(llmQueryBook);

        smartRefreshLayout = findViewById(R.id.srl_query_book);
        smartRefreshLayout.setEnableRefresh(false);
        if (bookList.size() == 0) {
            smartRefreshLayout.setEnableLoadMore(false);
        }
        smartRefreshLayout.setOnLoadMoreListener( (RefreshLayout refreshLayout) ->{
            page = bookList.size() / 12 + 1;
            sendQueryRequest(true, keyword, type, page);
        });
        sendQueryRequest(false, keyword, type, page);

        imgNull = findViewById(R.id.iv_null_lib_query);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
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
            if (page ==1 && event.getCode() == BaseCode.ERROR) {
                imgNull.setVisibility(View.VISIBLE);
                return;
            }
            if (page!=1) {
                adapterQuery.notifyDataSetChanged();
                return;
            }
            adapterQuery = new AdapterQuery(bookList);
            rvLibQuery.setAdapter(adapterQuery);
        });
    }

    private void sendQueryRequest(Boolean isLoadMore, String keyword, int type, int page) {
        if (!BaseApplication.checkNetConnecting()) {
            Toast.makeText(LibQueryActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isLoadMore) {
            showProgressDialog();
        }
        libraryPresenter.queryRequest(keyword, type, page);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LibQueryActivity.this, HomeActivity.class);
            intent.putExtra("lib_activity", true);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected Boolean isSlideBack() {
        return false;
    }

    @Override
    public void showBookDetail(BaseEvent<String> event) {

    }
}

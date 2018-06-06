package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.home.view.HomeActivity;
import com.febers.iuestc.adapter.AdapterHistory;
import com.febers.iuestc.adapter.AdapterQuery;
import com.febers.iuestc.module.library.contract.LibraryContract;
import com.febers.iuestc.module.library.model.BeanBook;
import com.febers.iuestc.module.library.contract.ILibraryPresenter;
import com.febers.iuestc.module.library.contract.LibraryPresenterImp;
import com.febers.iuestc.utils.CustomSharedPreferences;
import com.febers.iuestc.view.CustomProgressDialog;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class LibQueryActivity extends BaseActivity implements LibraryContract.View{

    private static final String TAG = "LibQueryActivity";
    private RecyclerView rvLibQuery;
    private LinearLayoutManager llmQueryBook;
    private AdapterQuery adapterQuery;
    private AdapterHistory adapterHistory;
    private List<BeanBook> beanBookList = new ArrayList<>();
    private Toolbar toolbar;
    private String keyword;
    private String type;
    private String position;
    private int status;
    private int page = 1;
    private String nextPageUrl;
    private LibraryContract.Presenter libraryPresenter;
    private SmartRefreshLayout smartRefreshLayout;
    private SearchView searchView;
    private CustomProgressDialog progressDialog;

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
            .edge(true)
            .build();
        Slidr.attach(this, config);
        return R.layout.activity_lib_query;
    }

    @Override
    protected void initView() {
        libraryPresenter = new LibraryPresenterImp(this);
        Intent intent = getIntent();
        if (intent.getBooleanExtra("history", false)) {
            sendHistoryRequest(false, page);
            initHistoryView();
            return;
        }
        {
            keyword = intent.getStringExtra("keyword");
            type = intent.getStringExtra("type");
            position = intent.getStringExtra("position");
            status = intent.getIntExtra("status", 0);
            nextPageUrl = "null";
        }
        sendQueryRequest(false, keyword, type, position, status, page, nextPageUrl);
        initQueryView();
    }


    private void initQueryView() {
        toolbar = findViewById(R.id.tb_lib_query);
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
        llmQueryBook = new LinearLayoutManager(this);
        llmQueryBook.setSmoothScrollbarEnabled(true);
        rvLibQuery.setLayoutManager(llmQueryBook);
        smartRefreshLayout = findViewById(R.id.srl_query_book);
        smartRefreshLayout.setOnLoadMoreListener( (@NonNull RefreshLayout refreshLayout) -> {
            if (beanBookList.size()==0) {
                return;
            }
            if (nextPageUrl == "null") {
                smartRefreshLayout.finishLoadMore(false);
            } else {
                page = beanBookList.size() / 12 + 1;
                sendQueryRequest(true, keyword, type, position, status, page, nextPageUrl);
            }
        });
        smartRefreshLayout.setEnableRefresh(false);
        searchView = findViewById(R.id.sv_activity_query);
        searchView.setQueryHint("请输入搜索内容");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                beanBookList.clear();
                nextPageUrl = "null";
                sendQueryRequest(false, keyword, type, position, status, page, nextPageUrl);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void initHistoryView() {
        toolbar = findViewById(R.id.tb_lib_query);
        toolbar.setTitle("我的借阅");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener((View v) -> {
            Intent intent = new Intent(LibQueryActivity.this, HomeActivity.class);
            intent.putExtra("lib_activity", true);
            startActivity(intent);
            finish();
        });
        rvLibQuery = findViewById(R.id.rv_lib_query);
        llmQueryBook = new LinearLayoutManager(this);
        llmQueryBook.setSmoothScrollbarEnabled(true);
        rvLibQuery.setLayoutManager(llmQueryBook);
        smartRefreshLayout = findViewById(R.id.srl_query_book);
        smartRefreshLayout.setOnLoadMoreListener( (@NonNull RefreshLayout refreshLayout) -> {
            if (beanBookList.size()==0) {
                return;
            }
            if (page == 1) {
                smartRefreshLayout.finishLoadMore(false);
            } else {
                page = beanBookList.size() / 12 + 1;
                Log.d(TAG, "onLoadMore: page" + page);
                sendHistoryRequest(true, page);
            }
        });
        smartRefreshLayout.setEnableRefresh(false);
        searchView = findViewById(R.id.sv_activity_query);
        //searchView.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) searchView.getLayoutParams();
        params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        searchView.setLayoutParams(params);
    }

    @Override
    public void showQuery(String status, String nextPageUrl, List<BeanBook> bookList) {
        dismissProgressDialog();
        smartRefreshLayout.finishLoadMore(true);
        this.nextPageUrl = nextPageUrl;
        if (status.contains("加载")) {
            beanBookList.clear();
            beanBookList.addAll(bookList);
            runOnUiThread( () -> {
                adapterQuery.notifyDataSetChanged();
                return;
            });
        }
        beanBookList = bookList;
        if (beanBookList == null) {
            return;
        }
        runOnUiThread( () -> {
            adapterQuery = new AdapterQuery(beanBookList);
            rvLibQuery.setAdapter(adapterQuery);
        });
    }

    @Override
    public void showHistory(List<BeanBook> list) {
        dismissProgressDialog();
        try{
            smartRefreshLayout.finishLoadMore(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        beanBookList = list;
        if (beanBookList == null) {
            page = 1;
            return;
        }
        page +=1;
        runOnUiThread( () -> {
            adapterHistory = new AdapterHistory(beanBookList);
            rvLibQuery.setAdapter(adapterHistory);
        });
    }

    private void sendQueryRequest(Boolean isLoadMore, String keyword, String type, String position,
                                  int status, int page, String nextPageUrl) {
        if (!BaseApplication.checkNetConnecting()) {
            Toast.makeText(LibQueryActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((!isLoadMore) || (!CustomSharedPreferences.getInstance().get("get_book_history", false))) {
            showProgressDialog();
        }
        libraryPresenter.queryRequest(keyword, type, position, status, page, nextPageUrl);
    }

    private void sendHistoryRequest(Boolean isRefresh, int page) {
        if (!BaseApplication.checkNetConnecting()) {
            Toast.makeText(LibQueryActivity.this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRefresh || (!CustomSharedPreferences.getInstance().get(BaseApplication.getContext()
                .getString(R.string.sp_library_history), false))) {
            showProgressDialog();
        }
        libraryPresenter.historyRequest(isRefresh, page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lib_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_lib_history_refresh:
                SharedPreferences.Editor editor = BaseApplication.getContext().getSharedPreferences("book_history", 0).edit();
                editor.clear();
                editor.commit();
                beanBookList.clear();
                sendHistoryRequest(true, 1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
}

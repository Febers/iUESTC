package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.presenter.LibraryContract;
import com.febers.iuestc.module.library.presenter.LibraryPresenterImp;
import com.febers.iuestc.util.WebViewUtil;

import java.util.List;

public class LibDetailActivity extends BaseSwipeActivity implements LibraryContract.View {

    private WebView webView;

    @Override
    protected int setView() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_lib_detail;
    }

    @Override
    protected String setToolbarTitle() {
        return "图书详情";
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView = findViewById(R.id.webview_lib_detail);
        sendQuest(url);
    }

    private void sendQuest(String url) {
        showProgressDialog();
        LibraryContract.Presenter presenter = new LibraryPresenterImp(this);
        presenter.bookDetailRequest(url);
    }

    @Override
    public void showQuery(BaseEvent<List<BeanBook>> event) {
    }

    @Override
    public void showBookDetail(BaseEvent<String> event) {
        dismissProgressDialog();
        runOnUiThread(()-> webView.loadDataWithBaseURL(null, event.getDate(),
                "text/html","UTF-8", null));
    }

    @Override
    protected void onDestroy() {
        WebViewUtil.destroyWebView(webView);
        super.onDestroy();
    }
}

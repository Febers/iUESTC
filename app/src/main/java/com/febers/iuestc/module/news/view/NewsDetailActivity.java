package com.febers.iuestc.module.news.view;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.net.WebViewConfigure;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public class NewsDetailActivity extends BaseSwipeActivity {

    private static final String TAG = "NewsDetailActivity";
    private WebView webView;
    private String newsText;
    private String newsTitle;
    private String newsUrl;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected int setView() {
        Intent intent = getIntent();
        newsText = intent.getStringExtra("text");
        newsTitle = intent.getStringExtra("title");
        newsUrl = intent.getStringExtra("url");
        return R.layout.activity_news_detail;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_news_detail;
    }

    @Override
    protected void initView() {
        collapsingToolbarLayout = findViewById(R.id.ctl_news);
        collapsingToolbarLayout.setTitle(newsTitle);
        webView = findViewById(R.id.webview_news);

        new WebViewConfigure.Builder(this, webView)
                .setOpenUrlOut(true)
                .setDomEnabled(true)
                .builder();

        webView.loadDataWithBaseURL("http://www.jwc.uestc.edu.cn", newsText, "text/html","UTF-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_news_to_source:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(newsUrl));
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}

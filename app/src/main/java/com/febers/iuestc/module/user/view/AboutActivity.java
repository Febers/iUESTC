package com.febers.iuestc.module.user.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

public class AboutActivity extends BaseActivity {

    private CardView cvAboutApp;
    private CardView cvAboutDeveloper;

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
                .edge(true)
                .build();
        Slidr.attach(this, config);
        return R.layout.activity_custom_about;
    }

    @Override
    protected void findViewById() {
        cvAboutApp = findViewById(R.id.cv_about_app);
        cvAboutDeveloper = findViewById(R.id.cv_about_developer);
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_about);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        cvAboutApp.setOnClickListener( (v) -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_web)));
            startActivity(Intent.createChooser(i, "访问App主页"));
        });
        cvAboutDeveloper.setOnClickListener( (v) -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.developer_github)));
            startActivity(Intent.createChooser(i, "访问开发者Github主页"));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

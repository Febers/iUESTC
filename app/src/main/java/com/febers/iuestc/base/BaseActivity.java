package com.febers.iuestc.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ThemeUtil;
import com.febers.iuestc.util.ToastUtil;
import com.febers.iuestc.view.custom.CustomProgressDialog;
import com.febers.iuestc.view.custom.CustomSupportActivity;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity extends CustomSupportActivity implements BaseView {

    protected CustomProgressDialog progressDialog;

    protected abstract int setView();

    protected void findViewById(){ }

    protected int setToolbar() {
        return -1;
    }

    protected String setToolbarTitle() {
        return null;
    }

    protected int setMenu() {
        return R.menu.default_menu;
    }

    protected Boolean registerEventBus() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseTheme();
        setContentView(setView());
        findViewById();

        if (setToolbar() != -1) {
            Toolbar toolbar = findViewById(setToolbar());
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            if (setToolbarTitle() != null) {
                toolbar.setTitle(setToolbarTitle());
            }
        }

        initView();
    }

    protected abstract void initView();

    private void chooseTheme() {
        int themeCode = SPUtil.INSTANCE().get("theme_code", 9);
        setTheme(ThemeUtil.getTheme(themeCode));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(setMenu(), menu);
        return true;
    }

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this);
        }
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            runOnUiThread( () -> progressDialog.dismiss());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (registerEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onError(String error) {
        runOnUiThread(()-> ToastUtil.showShortToast(error));
    }
}

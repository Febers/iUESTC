package com.febers.iuestc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.febers.iuestc.R;
import com.febers.iuestc.view.MyProgressDialog;

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    protected MyProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        findViewById();
        initView();
    }

    protected abstract int setView();
    protected abstract void findViewById();
    protected abstract void initView();

    protected int getContentView() {
        return setView();
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new MyProgressDialog(this);
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    protected String getStringById(int stringId) {
        return BaseApplication.getContext().getString(stringId);
    }
}

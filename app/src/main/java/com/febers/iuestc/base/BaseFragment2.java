/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-3 下午10:31
 *
 */

package com.febers.iuestc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.febers.iuestc.view.custom.CustomProgressDialog;


public abstract class BaseFragment2 extends MySupportFragament implements BaseView {

    protected static String PARAMTER_1 = "param1";
    protected Context mContext = BaseApplication.getContext();
    protected CustomProgressDialog mProgressDialog;
    private View view;

    protected abstract int setContentView();

    protected int setMenu(){return -1;}

    protected  void initView() {}

    protected void findView() {}

    protected Boolean registeEventBus() {
        return false;
    }

    //获取设置的布局
    protected View getContentView() {
        return view;
    }

    //找出对应的控件
    protected <T extends View> T findViewById(int id) {
        return (T) getContentView().findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(setContentView(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
        initView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (setMenu() != -1) {
            inflater.inflate(setMenu(), menu);
        }
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(getContext());
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        if (getActivity() != null) {
            getActivity().runOnUiThread( () -> mProgressDialog.dismiss());
        }
    }

    @Override
    public void onError(String error) {
        dismissProgressDialog();
        if (getActivity() != null) {
            getActivity().runOnUiThread( () -> Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show());
        }
    }
}

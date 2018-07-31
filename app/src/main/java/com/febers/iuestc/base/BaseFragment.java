/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午12:57
 *
 */

package com.febers.iuestc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.febers.iuestc.view.custom.CustomProgressDialog;

public abstract class BaseFragment extends Fragment implements BaseView{

    protected final String TAG = "BaseFragment";
    protected Context mContext = BaseApplication.getContext();
    protected CustomProgressDialog mProgressDialog;
    protected boolean isInit = false;
    protected boolean isLoad = false;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(setContentView(), container, false);
        isInit = true;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        isCanLoadData();
    }

    //视图是否已经对用户可见，系统的方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    //视图销毁的时候讲Fragment是否初始化的状态变为false
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    //获取设置的布局
    protected View getContentView() {
        return view;
    }

    //找出对应的控件
    protected <T extends View> T findViewById(int id) {
        return (T) getContentView().findViewById(id);
    }

    //设置Fragment要显示的布局
    protected abstract int setContentView();

    //当视图初始化并且对用户可见的时候去真正的加载数据
    protected abstract void lazyLoad();

    //初始化
    protected abstract void initView();

    //当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
    protected void stopLoad() { }

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

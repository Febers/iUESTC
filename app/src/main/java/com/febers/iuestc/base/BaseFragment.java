package com.febers.iuestc.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.util.ToastUtil;
import com.febers.iuestc.view.custom.CustomProgressDialog;
import com.febers.iuestc.view.custom.CustomSupportFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public abstract class BaseFragment extends CustomSupportFragment implements BaseView {

    private static final String TAG = "BaseFragment";
    protected static String PARAMETER = "param1";
    protected CustomProgressDialog progressDialog;
    private View view;

    protected abstract int setContentView();

    protected int setToolbar() {
        return -1;
    }

    protected String setToolbarTitle() {
        return null;
    }

    protected int setMenu() {
        return -1;
    }

    protected  void initView() { }

    protected void findView() { }

    protected Boolean registerEventBus() {
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

        if (setToolbar() != -1) {
            Toolbar toolbar = findViewById(setToolbar());
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            if (setMenu() != -1) {
                toolbar.inflateMenu(setMenu());    //防止activity销毁之后menu消失
            }
            if (setToolbarTitle() != null) {
                toolbar.setTitle(setToolbarTitle());
            }
        }

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
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(getContext());
        }
        if (getActivity() == null) return;
        if (getActivity().isFinishing()) return;
        progressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        if (getActivity() != null) {
            getActivity().runOnUiThread( () -> progressDialog.dismiss());
        }
    }

    @Override
    public void onError(String error) {
        dismissProgressDialog();
        if (getActivity() != null) {
            getActivity().runOnUiThread( () ->
                    ToastUtil.showShortToast(error)
            );
        }
    }
}

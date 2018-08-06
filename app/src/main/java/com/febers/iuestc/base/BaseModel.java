/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午3:02
 *
 */

package com.febers.iuestc.base;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.util.CustomSharedPreferences;

public abstract class BaseModel<P extends BasePresenter> {

    protected Boolean FIRST_TRY = true;
    protected int TRY_TIMES = 1;
    protected P presenter;

    public BaseModel(P presnter) {
        this.presenter = presnter;
    }

    public BaseModel() {
    }

    public static final String NET_TIMEOUT = "网络超时";
    public static final String NET_ERROR = "网络错误";
    public static final String UNKONOW_ERROR = "未知错误";
    public static final String LOGIN_STATUS_ERRO = "登录状态改变，请重新登录";

    public static final int UNDERGRADUATE = 0;
    public static final int POSTGRADUATE = 1;

    protected Context mContext = BaseApplication.getContext();

    protected int mStudentType = CustomSharedPreferences.getInstance()
            .get(mContext.getString(R.string.sp_student_type), 0);

    protected Boolean isLogin() {
        return CustomSharedPreferences.getInstance().get(mContext.getString(R.string.sp_is_login), false);
    }

    protected String getStringById(int id) {
        return mContext.getString(id);
    }

    protected void serviceError(String error) {
        presenter.errorResult(error);
    }
}

package com.febers.iuestc.base;

import android.content.Context;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.base.edu.EduPresenter;
import com.febers.iuestc.util.SPUtil;

public abstract class BaseModel<P extends BasePresenter> {

    private static final String TAG = "BaseModel";
    protected P presenter;

    public BaseModel(P presenter) {
        this.presenter = presenter;
    }

    protected BaseModel() {
    }

    protected static final String NET_TIMEOUT = "网络超时";
    protected static final String NET_ERROR = "网络错误";
    protected static final String UNKNOWN_ERROR = "未知错误";
    protected static final String LOGIN_STATUS_ERROR = "登录状态改变，请重新登录";
    protected static final String LOGIN_EXCEPTION = "登录出错";
    protected static final String LOGIN_ADDITION_ERROR = "获取登录环境出错";
    protected static final String LOGIN_PARAM_ERROR = "帐号或密码错误，请重新登录";

    protected static final int UNDERGRADUATE = 0;
    protected static final int POSTGRADUATE = 1;

    protected Context mContext = MyApp.getContext();

    protected int mStudentType = SPUtil.INSTANCE()
            .get(mContext.getString(R.string.sp_student_type), 0);

    protected void serviceError(String error) {
        presenter.errorResult(error);
    }

    protected void getHttpData() { }

    protected Boolean userAuthenticate(String html) {
        if (html.contains("用户登录")) {
            if (presenter instanceof EduPresenter) {
                EduPresenter p = (EduPresenter)presenter;
                p.loginStatusLoss();
            }
            return false;
        }
        return true;
    }
}

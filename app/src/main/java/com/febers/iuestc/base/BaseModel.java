package com.febers.iuestc.base;

import android.content.Context;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.edu.EduPresenter;
import com.febers.iuestc.util.SPUtil;

public abstract class BaseModel<P extends BasePresenter> {

    private static final String TAG = "BaseModel";
    protected Boolean FIRST_TRY = true;
    protected int TRY_TIMES = 1;
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

    protected Boolean isLogin() {
        return SPUtil.INSTANCE().get(mContext.getString(R.string.sp_is_login), false);
    }

//    protected String getStringById(int id) {
//        return mContext.getString(id);
//    }

    protected void serviceError(String error) {
        presenter.errorResult(error);
    }

    protected void getHttpData() {}

    /**
     * 由于教务系统的不稳定，Cookie的保持时间可能很短
     * 因此当Cookie失效的时候，需要重新登录
     * 借助LoginModelImpl的重新的登录功能更新Cookie
     * @param html 源网页
     * @return 如果出现重复登录，说明帐号在别处登录，此时一般再次发送请求即可，返回false
     * 如果出现登录规则，说明需要重新登录，如果重新登录成功，那么再次请求页面
     * TRY_TIMES用来标记请求的次数，一个实例只能有两次请求的机会
     * 如果再次出现登录规则，则需要重新手动登录，调用P 的loginStatusFail方法
     *
     * 上面已废弃，如果出现登录规则，直接重新手动登录
     */
    protected Boolean userAuthenticate(String html) {
        if (html.contains("重复登录")) {
            if (TRY_TIMES <= 2) {
                getHttpData();
                TRY_TIMES++;
            } else {
                presenter.errorResult("获取考试信息出错");
            }
            return false;
        }
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

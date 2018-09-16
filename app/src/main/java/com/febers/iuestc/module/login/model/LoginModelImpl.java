/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.login.model;

import android.util.Log;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.presenter.LoginContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.CustomSPUtil;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginModelImpl extends LoginContract.Model {

    private static final String TAG = "LoginModelImpl";
    private String mId;
    private String mPw;
    private LoginContract.Presenter loginPresenter;

    public LoginModelImpl(LoginContract.Presenter presenter) {
        super(presenter);
        this.loginPresenter = presenter;
    }

    public LoginModelImpl() {}

    @Override
    public void loginService(String id, String pw) {
        mId = id;
        mPw = pw;
        new Thread(this::getLoginParameters).start();
    }

    /**
     * 在已经登录的情况下重新登录，以进行后续操作
     * 切记不能在主线程调用
     */
    @Override
    public Boolean reloginService() {
        if (!CustomSPUtil.getInstance().get(getStringById(R.string.sp_is_login), false)) {
            return false;
        }
        mId = CustomSPUtil.getInstance().get(getStringById(R.string.sp_user_id), "");
        mPw = CustomSPUtil.getInstance().get(getStringById(R.string.sp_user_pw), "");
        return getLoginParameters();
    }

    private Boolean getLoginParameters() {
        try {
            OkHttpClient mClient = SingletonClient.getInstance();
            Request request = new Request.Builder()
                    .url(ApiUtil.BEFORE_LOGIN_URL)
                    .build();
            Response response = mClient.newCall(request).execute();
            String loginHtml = response.body().string();

            Document document = Jsoup.parse(loginHtml);
            Elements elements = document.select("input[type=hidden]");
            Element lt = elements.get(0);
            String mLt = lt.attr("value");
            Element execution = elements.get(2);
            String mExecution = execution.attr("value");

            return loginWithLAndE(mId, mPw, mLt, mExecution);
        } catch (Exception e) {
            e.printStackTrace();
            sendLoginResult(BaseCode.ERROR, LOGIN_ADDITION_ERROR);
            return false;
        }
    }

    private Boolean loginWithLAndE(String id, String pw, String lt, String exec) throws Exception{
        FormBody formBody = new FormBody.Builder()
                .add("username",id)
                .add("password",pw)
                .add("lt",lt)
                .add("_eventId","submit")
                .add("dllt","userNamePasswordLogin")
                .add("execution",exec)
                .add("rmShown","1")
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(ApiUtil.LOGIN_URL)
                .build();
        OkHttpClient client = SingletonClient.getInstance();

        Response response = client.newCall(request).execute();
        String mLoginHtml = response.body().string();
        return resolveLogin(mLoginHtml);
    }

    /**
     *  通过解析网页源码判断是否成功登录
     * @param html 网页源码
     * @return 是否成功登陆
     */
    private Boolean resolveLogin(String html) {
        Log.i(TAG, "resolveLogin: " + html);
        try {
            Document document = Jsoup.parse(html);
            Elements elements = document.select("li");
            if (elements.size() == 0) {
                sendLoginResult(BaseCode.ERROR, LOGIN_ADDITION_ERROR);
                return false;
            }
            if (html.contains("有误")) {
                sendLoginResult(BaseCode.ERROR, LOGIN_PARAM_ERROR);
                return false;
            }
            for(Element e:elements) {
                if (e.text().contains("个人") || e.text().contains("教务")){
                    CustomSPUtil.getInstance().put(getStringById(R.string.sp_user_id), mId);
                    CustomSPUtil.getInstance().put(getStringById(R.string.sp_user_pw), mPw);
                    CustomSPUtil.getInstance().put(getStringById(R.string.sp_is_login), true);
                    sendLoginResult(BaseCode.UPDATE, "");
                    /*
                     * 判断学生属性
                     * 然后保存
                     * 本科生(0)、研究生(1)
                     */
                    CustomSPUtil.getInstance().put(getStringById(R.string.sp_student_type), 0);
                    return true;
                }
            }
        } catch (Exception e) {
            sendLoginResult(BaseCode.ERROR, LOGIN_EXCEPTION);
            return false;
        }
        sendLoginResult(BaseCode.ERROR, UNKNOWN_ERROR);
        return false;
    }

    private void sendLoginResult(int code, String message) {
        if (loginPresenter != null) {
            loginPresenter.loginResult(new BaseEvent<>(code, message));
        }
        if (code == BaseCode.ERROR) {
            SingletonClient.reset();
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-31 下午5:14
 *
 */

package com.febers.iuestc.module.login.model;

import android.util.Log;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.presenter.LoginContract;
import com.febers.iuestc.util.CustomSPUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoginResolver implements ILoginResolver{

    private static final String TAG = "LoginResolver";
    private LoginContract.Presenter loginPresenter;

    public LoginResolver(LoginContract.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void resolve(String html) {
        try {
            if (html.contains("mobileUsername")) {
                loginPresenter.sendIdAndPwFunc(new BaseEvent<>(BaseCode.UPDATE, idAndPwFunc()));
                return;
            }
            Document document = Jsoup.parse(html);
            Elements elements = document.select("li");
            if (elements.size() == 0) {
                Log.i(TAG, "checkLoginResult: 登录无效");
                return;
            }
            for(Element e:elements) {
                if (e.text().contains("个人") || e.text().contains("教务")){
                    Log.i(TAG, "checkLoginResult: 登录成功");
                    loginPresenter.loginResult(new BaseEvent(BaseCode.UPDATE, "成功"));
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "checkLoginResult: 登录出错");
        }
    }

    /**
     * 向login界面的用户名输入框注入id
     *
     * @return js函数
     */
    private String  idAndPwFunc() {
        String userId = CustomSPUtil.getInstance().get(BaseApplication.getContext()
                .getString(R.string.sp_user_id), "");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("javascript:function fun(){document.getElementById('mobileUsername').value='"+userId+"';");
        stringBuffer.append("document.getElementById('mobilePassword').value='" + "" + "';}");
        return stringBuffer.toString();
    }
}

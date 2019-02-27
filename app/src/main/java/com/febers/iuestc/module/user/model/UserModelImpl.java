/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午2:05
 *
 */

package com.febers.iuestc.module.user.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanUser;
import com.febers.iuestc.module.user.presenter.UserContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.SPUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserModelImpl extends BaseModel implements UserContract.Model {

    private static final String TAG = "UserModelImpl";

    private UserContract.Presenter userPresenter;

    public UserModelImpl(UserContract.Presenter userPresenter) {
        this.userPresenter = userPresenter;
    }

    @Override
    public void userDetailService(Boolean isRefresh) {
        new Thread(()-> {
            if (!isRefresh) {
                localDateService();
                return;
            }
            getHttpData();
        }).start();
    }

    @Override
    protected void getHttpData() {
        OkHttpClient client = SingletonClient.getInstance();
        Request request = new Request.Builder()
                .url("http://eams.uestc.edu.cn/eams/stdDetail.action")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            if (!userAuthenticate(result)) {
                return;
            }
            resolveHtml(result);
        } catch (Exception e) {
            e.printStackTrace();
            userPresenter.loginStatusFail();
        }
    }

    private void resolveHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table").select("td");
        BeanUser user = new BeanUser();
        if (elements.size() < 10) {
            userPresenter.userDetailResult(new BaseEvent<>(BaseCode.ERROR, user));
            return;
        }
        for (int i = 0; i < elements.size(); i++) {
            try {
                String  param = elements.get(i).text();
                String  value = elements.get(i+1).text();
                if (param.contains("学号")) {
                    user.setId(value);
                    continue;
                }
                if (param.equals("姓名：")) {
                    user.setChineseName(value);
                    continue;
                }
                if (param.contains("英文")) {
                    user.setEnglishName(value);
                    continue;
                }
                if (param.contains("性别")) {
                    user.setSex(value);
                    continue;
                }
                if (param.contains("年级")) {
                    user.setGrade(value);
                    continue;
                }
                if (param.contains("学制")) {
                    user.setStudyYear(value);
                    continue;
                }
                if (param.contains("学历")) {
                    user.setType(value);
                    continue;
                }
                if (param.contains("院系")) {
                    user.setSchool(value);
                    continue;
                }
                if (param.equals("专业：")) {
                    user.setMajor(value);
                    continue;
                }
                if (param.contains("入校")) {
                    user.setIntoTime(value);
                    continue;
                }
                if (param.contains("毕业")) {
                    user.setOutTime(value);
                    continue;
                }
                if (param.contains("班级")) {
                    user.setUserClass(value);
                    continue;
                }
                if (param.contains("所属校区：")) {
                    user.setPosition(value);
                    continue;
                }
                if (param.contains("移动电话")) {
                    user.setPhone(value);
                    continue;
                }
                if (param.contains("通讯")) {
                    user.setAddress(value);
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
        }
        userPresenter.userDetailResult(new BaseEvent<>(BaseCode.UPDATE, user));
        SPUtil.getInstance()
                .put(getStringById(R.string.sp_user_name), user.getChineseName());
        SPUtil.getInstance()
                .put(getStringById(R.string.sp_user_id), user.getId());
        UserStore.saveUserToFile(user);
    }

    private void localDateService() {
        userPresenter.userDetailResult(new BaseEvent<>(BaseCode.LOCAL, UserStore.getUserByFile()));
    }
}

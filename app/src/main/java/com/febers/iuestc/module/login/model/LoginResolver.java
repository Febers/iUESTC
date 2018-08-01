/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-31 下午5:14
 *
 */

package com.febers.iuestc.module.login.model;

import android.util.Log;

import com.febers.iuestc.base.BaseEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoginResolver implements ILoginResolver{

    private static final String TAG = "LoginResolver";

    @Override
    public void resolve(String html) {
        try {
            Document document = Jsoup.parse(html);
            Elements elements = document.select("li");
            if (elements.size() == 0) {
                Log.i(TAG, "checkLoginResult: 登录无效");
                return;
            }
            for(Element e:elements) {
                if (e.text().contains("个人") || e.text().contains("教务")){
                    Log.i(TAG, "checkLoginResult: 登录成功");
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "checkLoginResult: 登录出错");
        }
    }
}

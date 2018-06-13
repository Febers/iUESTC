/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 检查重复登录的类，只有一个方法
 * 如果重复登录则返回新的网页值
 * 否则返回no
 */

public class RepeatLoginUtil {

    public static String check(String html) {

        if (html.contains("重复登录")) {
            Log.d("", "getCourseHtml: 检测到一次重复登录");
            Document document = Jsoup.parse(html);
            Elements elements = document.select("a");
            return elements.get(0).attr("href");
        } else {
            return "no";
        }
    }

    private final String REPEAT_LOGIN = "\n" +
            "<html xml:lang=\"zh\" xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<body><h2>当前用户存在重复登录的情况，已将之前的登录踢出：</h2><pre>用户名：2016030301007 登录时间：2018-03-19 19:14:36.965 操作系统：Unknown  " +
            "浏览器：Unknown </pre><br>请<a href=\"http://eams.uestc.edu.cn/eams/home.action\">点击此处</a>继续</body></html>";
}

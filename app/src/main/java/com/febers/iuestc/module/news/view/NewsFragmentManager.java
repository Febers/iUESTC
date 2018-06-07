/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午9:23
 *
 */

package com.febers.iuestc.module.news.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 返回三个Fragment
 * 分别对应不同的新闻类型
 */
public class NewsFragmentManager {

    private static final String TAG = "NewsFragmentManager";
    private static NewsFragment newsFragment1;
    private static NewsFragment newsFragment2;
    private static NewsFragment newsFragment3;

    public static Fragment getInstance(int type, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("position", position);

        if (position == 0) {
            if (newsFragment1 == null) {
                newsFragment1 = NewsFragment.getInstance(bundle);
            }
            return newsFragment1;
        }
        if (position == 1) {
            if (newsFragment2 == null) {
                newsFragment2 = NewsFragment.getInstance(bundle);
            }
            return newsFragment2;
        }
        if (position == 2) {
            if (newsFragment3 == null) {
                newsFragment3 = NewsFragment.getInstance(bundle);
            }
            return newsFragment3;
        }
        return new NewsFragment();
    }

    public static void clearAllFragment() {
        newsFragment1 = null;
        newsFragment2 = null;
        newsFragment3 = null;
    }
}

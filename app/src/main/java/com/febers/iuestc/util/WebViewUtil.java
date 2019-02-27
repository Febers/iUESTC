/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-3 上午12:54
 *
 */

package com.febers.iuestc.util;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

public class WebViewUtil {

    public static void destroyWebView(WebView webView) {
        if (webView != null) {
            // 如果先调用destroy()方法，则会触发if (isDestroyed()) return;这一行代码
            // 需要先onDetachedFromWindow()，再destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
    }
}

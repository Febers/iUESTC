/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-28 下午7:05
 *
 */

package com.febers.iuestc.net;

import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {

    private static final String TAG = "CustomWebViewClient";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT > 21) {
            view.loadUrl(request.getUrl().toString());
        }
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        //解析网页源码
        view.loadUrl("javascript:HTMLOUT.processHTML(document.documentElement.outerHTML);");
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-4 下午10:53
 *
 */

package com.febers.iuestc.net;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NoImgWebViewClient extends WebViewClient {

    private static final String TAG = "NoImgWebViewClient";
    @TargetApi(21)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (request.getUrl().toString().contains("image")||
                request.getUrl().toString().contains("png")||
                request.getUrl().toString().contains("jpg")) {
            return new WebResourceResponse(null, null, null);
        } else {
            return super.shouldInterceptRequest(view, request);
        }
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (url.contains("image")||
                url.contains("png")||
                url.contains("jpg")) {
            return new WebResourceResponse(null, null, null);
        } else {
            return super.shouldInterceptRequest(view, url);
        }
    }
}

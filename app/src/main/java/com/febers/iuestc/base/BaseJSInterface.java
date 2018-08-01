/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-31 下午4:48
 *
 */

package com.febers.iuestc.base;

import android.webkit.JavascriptInterface;

public interface BaseJSInterface {

    @JavascriptInterface
    @SuppressWarnings("unused")
    void processHTML(String html);
}

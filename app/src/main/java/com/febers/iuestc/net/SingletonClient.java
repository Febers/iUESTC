/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:04
 *
 */

package com.febers.iuestc.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class SingletonClient {

    private static OkHttpClient singletonClient;

    private SingletonClient() {
    }

    public static OkHttpClient getInstance() {
        if (singletonClient == null) {
            synchronized (SingletonClient.class) {
                if (singletonClient == null) {
                    singletonClient = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .cookieJar(new CookiesManager())
                            .build();
                }
            }
        }
        return singletonClient;
    }

    public static void reset() {
        singletonClient = null;
    }
}

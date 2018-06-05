package com.febers.iuestc.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 23033 on 2018/3/13.
 */

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

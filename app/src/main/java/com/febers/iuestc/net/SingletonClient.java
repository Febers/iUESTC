package com.febers.iuestc.net;

import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.TlsVersion;

public class SingletonClient {

    private static final String TAG = "SingletonClient";

    private static OkHttpClient singletonClient;

    private SingletonClient() {
    }

    public static OkHttpClient getInstance() {
        if (singletonClient == null) {
            synchronized (SingletonClient.class) {
                if (singletonClient == null) {
                    singletonClient = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .cookieJar(new CustomCookiesManager())
                            .build();
                }
            }
        }
        return singletonClient;
    }

    public static void reset() {
        singletonClient = null;
        CookieManager.getInstance().removeAllCookie();
//        if (Build.VERSION.SDK_INT > 21) {
//            CookieManager.INSTANCE().removeAllCookies((Boolean value) -> {
//                    Log.i(TAG, "onReceiveValue: " + value);
//            });
//        }
    }
}

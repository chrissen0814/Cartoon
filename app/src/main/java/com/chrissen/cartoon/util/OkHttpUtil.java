package com.chrissen.cartoon.util;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by chris on 2017/11/15.
 */

public class OkHttpUtil {
    private static final String TAG = "HTTP";

    private static OkHttpClient sOkHttpUtil;
    private static class HttpLogger implements HttpLoggingInterceptor.Logger{

        @Override
        public void log(String message) {
            Log.i(TAG, message);
        }
    }

    public static OkHttpClient newInstance(){
        if (sOkHttpUtil == null) {
            synchronized (OkHttpUtil.class){
                if (sOkHttpUtil == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor(new HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20,TimeUnit.SECONDS)
                            .readTimeout(20,TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);
                    sOkHttpUtil = builder.build();
                }
            }
        }
        return sOkHttpUtil;
    }

}

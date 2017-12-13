package com.chrissen.cartoon.util;

import android.util.Log;

import com.chrissen.cartoon.CartoonApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.Response;
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
    private static Cache sCache = new Cache(CartoonApplication.getContext().getCacheDir(),10*1024*1024);


    public static OkHttpClient newInstance(){
        if (sOkHttpUtil == null) {
            synchronized (OkHttpUtil.class){
                if (sOkHttpUtil == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor(new HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                            .cache(sCache)
                            .addNetworkInterceptor(new CacheInterceptor())
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


    private static class CacheInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Response newResponse = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control","max-age=" + 3600 * 24 * 1)
                    .build();
            return newResponse;
        }
    }

}

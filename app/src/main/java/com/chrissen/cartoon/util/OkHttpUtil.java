package com.chrissen.cartoon.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by chris on 2017/11/15.
 */

public class OkHttpUtil {

    private static OkHttpClient sOkHttpUtil;

    public static OkHttpClient newInstance(){
        if (sOkHttpUtil == null) {
            synchronized (OkHttpUtil.class){
                if (sOkHttpUtil == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20,TimeUnit.SECONDS)
                            .readTimeout(20,TimeUnit.SECONDS);
                    sOkHttpUtil = builder.build();
                }
            }
        }
        return sOkHttpUtil;
    }

}

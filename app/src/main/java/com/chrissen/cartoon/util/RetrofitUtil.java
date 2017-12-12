package com.chrissen.cartoon.util;

import com.chrissen.cartoon.util.fastjsonfactory.FastJsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by chris on 2017/11/15.
 */

public class RetrofitUtil {

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private static RetrofitUtil sRetrofitUtil;

    public static RetrofitUtil newInstance(){
        if (sRetrofitUtil == null) {
            synchronized (RetrofitUtil.class){
                if (sRetrofitUtil == null) {
                    sRetrofitUtil = new RetrofitUtil();
                }
            }
        }
        return sRetrofitUtil;
    }

    private RetrofitUtil(){
        init();
    }

    private void init() {
        mOkHttpClient = OkHttpUtil.newInstance();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ConfigUtil.CARTOON_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new FastJsonConverterFactory())
                .build();
    }


    public <T> T getApiService(Class<T> clz){
        return mRetrofit.create(clz);
    }


}

package com.chrissen.cartoon.module.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.chrissen.cartoon.bean.AcgBean;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.OkHttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chris on 2017/11/19.
 */

public class AcgModel {

    public void saveAcg(){

        Observable.just(ConfigUtil.ACG_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, AcgBean>() {
                    @Override
                    public AcgBean apply(String s) throws Exception {
                        return getAcgBean();
                    }
                }).subscribe(new Observer<AcgBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AcgBean value) {
                if (value != null) {
                    Bitmap bitmap = getAcgBitmap(value);
                    if (bitmap != null) {
                        saveAcgBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    private AcgBean getAcgBean(){
        Call call = OkHttpUtil.newInstance().newCall(new Request.Builder().url(ConfigUtil.ACG_URL).build());
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                AcgBean acgBean = JSON.parseObject(response.body().string(),AcgBean.class);
                return acgBean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Bitmap getAcgBitmap(AcgBean acgBean){
        try {
            ResponseBody responseBody = OkHttpUtil.newInstance()
                    .newCall(new Request.Builder().url(acgBean.getImgUrl()).build())
                    .execute().body();
            InputStream inputStream = responseBody.byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveAcgBitmap(Bitmap bitmap){
        File appDir = new File(Environment.getExternalStorageDirectory(), ConfigUtil.APP_DIR);

        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = ConfigUtil.BG_IMAGE_NAME;
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

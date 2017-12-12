package com.chrissen.cartoon.module.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.OkHttpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chris on 2017/12/12.
 */

public class ImageModel {

    public void saveImage(){
        Observable.just(ConfigUtil.ER_CI_YUAN)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        return getBitmap(getImageUrl(s));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        if (bitmap != null) {
                            saveBitmap(bitmap);
                        }
                    }
                });
    }

    private String getImageUrl(String url){
        Call call = OkHttpUtil.newInstance().newCall(new Request.Builder().url(url).build());
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                String imageUrl = response.body().string();
                return imageUrl;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap getBitmap(String url){
        try {
            ResponseBody responseBody = OkHttpUtil.newInstance()
                    .newCall(new Request.Builder().url(url).build())
                    .execute().body();
            InputStream inputStream = responseBody.byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveBitmap(Bitmap bitmap){

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            File appDir = CartoonApplication.getContext().getExternalCacheDir();

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

}

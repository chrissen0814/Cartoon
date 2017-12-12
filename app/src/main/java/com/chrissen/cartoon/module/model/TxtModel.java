package com.chrissen.cartoon.module.model;

import android.os.Environment;

import com.chrissen.cartoon.CartoonApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by chris on 2017/12/12.
 */

public class TxtModel {

    public void copyFile(final String filePath){
        Observable.just(filePath)
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String s) throws Exception {
                        return new File(s);
                    }
                }).filter(new Predicate<File>() {
            @Override
            public boolean test(File file) throws Exception {
                return file.exists();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                                || !Environment.isExternalStorageRemovable()) {

                            File appDir = CartoonApplication.getContext().getExternalFilesDir(null);

                            String fileName = file.getName();
                            File newFile = new File(appDir, fileName);
                            FileInputStream fileInputStream = new FileInputStream(file);
                            byte[] data = new byte[1024];
                            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                            while (fileInputStream.read(data) != -1){
                                fileOutputStream.write(data);
                            }
                            fileInputStream.close();
                            fileOutputStream.close();

                        }
                    }
                });
    }

    public List<File> readFiles(){
        List<File> fileList = new ArrayList<>();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            File file = CartoonApplication.getContext().getExternalFilesDir(null);
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    fileList.add(files[i]);
                }
                return fileList;
            }

        }
        return null;
    }

}

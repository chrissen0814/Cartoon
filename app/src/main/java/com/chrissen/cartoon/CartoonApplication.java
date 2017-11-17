package com.chrissen.cartoon;

import android.app.Application;
import android.content.Context;

import com.chrissen.cartoon.dao.GreendaoManager;

/**
 * Created by chris on 2017/11/15.
 */

public class CartoonApplication extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initDB();
    }

    private void initDB() {
        GreendaoManager.getInstance(sContext);
    }

    public static Context getContext(){
        return sContext;
    }

}

package com.chrissen.cartoon.dao;

import android.content.Context;

import com.chrissen.cartoon.dao.greendao.DaoMaster;
import com.chrissen.cartoon.dao.greendao.DaoSession;

/**
 * Created by chris on 2017/11/15.
 */

public class GreendaoManager {

    private static final String DB_NAME = "cartoon.db";

    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreendaoManager sGreendaoManager;

    private GreendaoManager(Context context) {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DB_NAME);
        mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }


    public static GreendaoManager getInstance(Context context) {
        if (sGreendaoManager == null){
            synchronized (GreendaoManager.class){
                if (sGreendaoManager == null) {
                    sGreendaoManager = new GreendaoManager(context);
                }
            }
        }
        return sGreendaoManager;
    }

    public DaoMaster.DevOpenHelper getDevOpenHelper() {
        return mDevOpenHelper;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void closeDB() {
        if (mDevOpenHelper != null) {
            mDevOpenHelper.close();
        }
    }


}

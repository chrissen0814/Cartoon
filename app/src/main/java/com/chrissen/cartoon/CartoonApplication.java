package com.chrissen.cartoon;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.chrissen.cartoon.dao.GreendaoManager;

import es.dmoral.toasty.Toasty;

/**
 * Created by chris on 2017/11/15.
 */

public class CartoonApplication extends Application {

    private static Context sContext;
    private static FeedbackAgent sFeedbackAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initDB();
        initAVS();
        initToasty();
        initFeedbackAgent();
    }

    private void initFeedbackAgent() {
        sFeedbackAgent = new FeedbackAgent(this);
    }


    private void initAVS() {
        AVOSCloud.initialize(this,"CerxnIvmC3ETvKnKnyhtBJUn-gzGzoHsz","3DuTawa4tHmnxE14uAolpWJO");
        AVAnalytics.enableCrashReport(this,true);
        AVAnalytics.setAnalyticsEnabled(true);
    }

    private void initToasty() {
        Toasty.Config.getInstance()
                .setErrorColor(getResources().getColor(R.color.toast_error))
                .setSuccessColor(getResources().getColor(R.color.toast_success))
                .setTextColor(Color.BLACK)
                .apply();
    }

    private void initDB() {
        GreendaoManager.getInstance(sContext);
    }

    public static Context getContext(){
        return sContext;
    }

    public static FeedbackAgent getFeedbackAgent() {
        return sFeedbackAgent;
    }
}

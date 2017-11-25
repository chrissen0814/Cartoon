package com.chrissen.cartoon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVAnalytics;

/**
 * Created by chris on 2017/11/17.
 */

public abstract class BaseAbstractActivity extends AppCompatActivity {

    protected Activity mAct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mAct = this;
    }

    protected abstract void initViews();

    protected abstract void initParams();

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }
}

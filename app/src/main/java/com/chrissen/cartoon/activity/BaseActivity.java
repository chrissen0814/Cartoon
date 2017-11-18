package com.chrissen.cartoon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chris on 2017/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mAct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(layoutId());
        mAct = this;
        initViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
        initParams();
    }

    protected abstract int layoutId();

    protected abstract void initViews();

    protected abstract void initParams();



}

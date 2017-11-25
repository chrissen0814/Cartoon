package com.chrissen.cartoon.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chris on 2017/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mAct;



    protected abstract void initViews();

    protected abstract void initParams();



}

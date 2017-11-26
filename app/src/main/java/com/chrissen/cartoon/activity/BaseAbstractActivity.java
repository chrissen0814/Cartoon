package com.chrissen.cartoon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.avos.avoscloud.AVAnalytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 2017/11/17.
 */

public abstract class BaseAbstractActivity extends AppCompatActivity {

    List<View> bindClickView = new ArrayList<>();
    protected Activity mAct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mAct = this;
    }

    protected abstract void initViews();

    protected abstract void initParams();


    public void putBindClick(View view) {
        if (view == null)
            return;
        view.setClickable(false);
        bindClickView.add(view);
    }


    public void resetClick() {
        for (View view : bindClickView) {
            view.setClickable(true);
        }
        bindClickView.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetClick();
        AVAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetClick();
    }
}

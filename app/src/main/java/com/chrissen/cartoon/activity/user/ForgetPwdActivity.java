package com.chrissen.cartoon.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.user.ForgetPwdPresenter;
import com.chrissen.cartoon.module.view.ForgetPwdView;

public class ForgetPwdActivity extends AppCompatActivity implements ForgetPwdView {
    private EditText mEmailEt;

    private ForgetPwdPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        findViews();
        initParams();
    }

    private void initParams() {
        mPresenter = new ForgetPwdPresenter(this);
    }

    private void findViews() {
        mEmailEt = findViewById(R.id.email_et);
    }

    @Override
    public void onShowSuccess(Object obj) {

    }

    @Override
    public void onShowError(String errorMsg) {

    }

    public void onPostClick(View view) {

    }
}

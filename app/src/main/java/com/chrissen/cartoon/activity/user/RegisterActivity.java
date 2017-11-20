package com.chrissen.cartoon.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.user.RegisterPresenter;
import com.chrissen.cartoon.module.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter mPresenter;

    private EditText mNameEt , mEmailEt , mPwdEt , mConfirmPwdEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        initParams();
    }

    private void initParams() {
        mPresenter = new RegisterPresenter(this);
    }

    private void findViews() {
        mNameEt = findViewById(R.id.register_name_et);
        mEmailEt = findViewById(R.id.register_email_et);
        mPwdEt = findViewById(R.id.register_pwd_et);
        mConfirmPwdEt = findViewById(R.id.register_pwd_confirm_et);
    }

    @Override
    public void onSameName() {

    }

    @Override
    public void onEmailRegistered() {

    }

    @Override
    public void onShowSuccess(Object obj) {

    }

    @Override
    public void onShowError(String errorMsg) {

    }

    public void onSignInClick(View view) {
        String name = mNameEt.getText().toString();
        String email = mNameEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        String confirmPwd = mConfirmPwdEt.getText().toString();


    }



}

package com.chrissen.cartoon.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.user.RegisterPresenter;
import com.chrissen.cartoon.module.view.RegisterView;
import com.chrissen.cartoon.util.TextHelper;

import es.dmoral.toasty.Toasty;

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
        Toasty.error(this,getString(R.string.toast_register_user_name_same), Toast.LENGTH_SHORT,true).show();
    }

    @Override
    public void onEmailRegistered() {
        Toasty.error(this,getString(R.string.toast_register_email_registered), Toast.LENGTH_SHORT,true).show();
        finish();
    }

    @Override
    public void onShowSuccess(Object obj) {
        Toasty.success(this,getString(R.string.toast_register_success), Toast.LENGTH_SHORT,true).show();
        setResult(RESULT_OK);
    }

    @Override
    public void onShowError(String errorMsg) {
        Toasty.error(this,errorMsg, Toast.LENGTH_SHORT,true).show();
    }

    public void onSignInClick(View view) {
        String name = mNameEt.getText().toString();
        String email = mEmailEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        String confirmPwd = mConfirmPwdEt.getText().toString();
        boolean emailCorrect = TextHelper.isEmail(email);
        if (emailCorrect) {
            if (pwd.equals(confirmPwd)) {
                mPresenter.register(name,email,pwd);
            }else {
                Toasty.error(this,getString(R.string.toast_register_pwd_not_same), Toast.LENGTH_SHORT,true).show();
            }
        }else {
            Toasty.error(this,getString(R.string.toast_register_email_error), Toast.LENGTH_SHORT,true).show();
        }

    }


    public void onBackClick(View view) {
        finish();
    }


}

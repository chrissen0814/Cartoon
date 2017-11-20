package com.chrissen.cartoon.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.user.SignInPresenter;
import com.chrissen.cartoon.module.view.SignInView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SignInActivity extends AppCompatActivity implements SignInView {
    private static final int REGISTER_CODE = 20;

    private EditText mNameOrEmailEt , mPwdEt;
    private TextView mWelcomeTv;

    private SignInPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViews();
        initParams();
    }

    private void initParams() {
        mWelcomeTv.setFocusable(true);
        mWelcomeTv.requestFocus();
        YoYo.with(Techniques.RubberBand)
                .duration(800)
                .repeatMode(Animation.REVERSE)
                .repeat(Integer.MAX_VALUE)
                .playOn(findViewById(R.id.sign_in_welcome_tv));
        mPresenter = new SignInPresenter(this);
    }

    private void findViews() {
        mNameOrEmailEt = findViewById(R.id.sign_in_name_email_et);
        mPwdEt = findViewById(R.id.sign_in_pwd_et);
        mWelcomeTv = findViewById(R.id.sign_in_welcome_tv);
    }

    @Override
    public void onEmailNotFound() {

    }

    @Override
    public void onUserNotExist() {

    }

    @Override
    public void onUserNameMissing() {

    }

    @Override
    public void onShowSuccess(Object obj) {

    }

    @Override
    public void onShowError(String errorMsg) {

    }

    public void onSignClick(View view) {
        String nameOrEmail = mNameOrEmailEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        mPresenter.signIn(nameOrEmail,pwd);
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent , REGISTER_CODE);
    }

    public void onForgetPwdClick(View view) {
        startActivity(new Intent(this,ForgetPwdActivity.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REGISTER_CODE:
                if (requestCode == RESULT_OK) {
                    finish();
                }
                break;
        }
    }
}

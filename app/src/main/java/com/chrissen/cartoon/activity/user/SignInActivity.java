package com.chrissen.cartoon.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.activity.BaseAbstractActivity;
import com.chrissen.cartoon.module.presenter.user.SignInPresenter;
import com.chrissen.cartoon.module.view.SignInView;
import com.chrissen.cartoon.util.AnimUtil;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends BaseAbstractActivity implements SignInView {
    private static final int REGISTER_CODE = 20;
    private static final int FORGET_PWD = 21;

    private EditText mNameOrEmailEt , mPwdEt;
    private TextView mWelcomeTv;
    private ImageView mLockIv , mImageIv;
    private boolean isPwdVisible;
    private SignInPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        initParams();
    }

    @Override
    protected void initViews() {
        mImageIv = findViewById(R.id.sign_in_image_iv);
        mNameOrEmailEt = findViewById(R.id.sign_in_name_email_et);
        mPwdEt = findViewById(R.id.sign_in_pwd_et);
        mWelcomeTv = findViewById(R.id.sign_in_welcome_tv);
        mLockIv = findViewById(R.id.sign_in_lock_iv);
    }

    protected void initParams() {
        mWelcomeTv.setFocusable(true);
        mWelcomeTv.requestFocus();
        mPresenter = new SignInPresenter(this);
        AnimUtil.slideInFromUp(mImageIv,this);
        mPwdEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AnimUtil.slideOutFromUp(mImageIv,SignInActivity.this);
                }else {
                    AnimUtil.slideInFromUp(mImageIv,SignInActivity.this);
                }
            }
        });
        mNameOrEmailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    @Override
    public void onEmailNotFound() {
        Toasty.error(this,getString(R.string.toast_sign_in_email_not_found),Toast.LENGTH_SHORT,true).show();
    }

    @Override
    public void onUserNotExist() {
        Toasty.error(this,getString(R.string.toast_sign_in_user_not_exist),Toast.LENGTH_SHORT,true).show();
    }

    @Override
    public void onUserNameMissing() {
        Toasty.error(this,getString(R.string.toast_sign_in_user_name_error),Toast.LENGTH_SHORT,true).show();
    }

    @Override
    public void onShowSuccess(Object obj) {
        Toasty.success(this,getString(R.string.toast_sign_in_success),Toast.LENGTH_SHORT,true).show();
        finish();
    }

    @Override
    public void onShowError(String errorMsg) {
        Toasty.error(this,errorMsg,Toast.LENGTH_SHORT,true).show();
    }

    public void onSignClick(View view) {
        String nameOrEmail = mNameOrEmailEt.getText().toString();
        String pwd = mPwdEt.getText().toString();
        if (nameOrEmail != null && pwd != null && !nameOrEmail.equals("") && !pwd.equals("")) {
            mPresenter.signIn(nameOrEmail,pwd);
        }else {
            Toasty.error(this,getString(R.string.toast_sign_in_error), Toast.LENGTH_SHORT,true).show();
        }
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent , REGISTER_CODE);
    }

    public void onForgetPwdClick(View view) {
        Intent intent = new Intent(this, ForgetPwdActivity.class);
        startActivityForResult(intent,FORGET_PWD);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REGISTER_CODE:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;
            case FORGET_PWD:
                if(resultCode == RESULT_OK){

                }
                break;
        }
    }

    public void onLockClick(View view) {
        if (!isPwdVisible) {
            mLockIv.setImageResource(R.drawable.icon_unlock);
            mPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mPwdEt.setSelection(mPwdEt.getText().length());
            isPwdVisible = true;
        }else {
            mLockIv.setImageResource(R.drawable.icon_lock);
            mPwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mPwdEt.setSelection(mPwdEt.getText().length());
            isPwdVisible = false;
        }
    }
}

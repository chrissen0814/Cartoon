package com.chrissen.cartoon.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chrissen.cartoon.R;
import com.chrissen.cartoon.module.presenter.user.ForgetPwdPresenter;
import com.chrissen.cartoon.module.view.ForgetPwdView;
import com.chrissen.cartoon.util.TextHelper;

import es.dmoral.toasty.Toasty;

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
        Toasty.success(this,getString(R.string.toast_forget_success),Toast.LENGTH_SHORT,true).show();
        setResult(RESULT_OK);
    }

    @Override
    public void onEmailNotFound() {
        Toasty.error(this,getString(R.string.toast_email_not_found), Toast.LENGTH_SHORT,true).show();
    }

    @Override
    public void onShowError(String errorMsg) {
        Toasty.error(this,errorMsg,Toast.LENGTH_SHORT,true).show();
    }

    public void onPostClick(View view) {
        String email = mEmailEt.getText().toString();
        boolean correct = TextHelper.isEmail(email);
        if (correct) {
            mPresenter.forgetPwd(email);
        }else {
            Toasty.error(this,getString(R.string.toast_forget_fail),Toast.LENGTH_SHORT,true).show();
        }
    }

    public void onBackClick(View view) {
        finish();
    }
}

package com.chrissen.cartoon.module.presenter.user;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.chrissen.cartoon.module.model.user.SignInModel;
import com.chrissen.cartoon.module.view.SignInView;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class SignInPresenter {

    private SignInModel mModel;
    private SignInView mView;

    public SignInPresenter(SignInView view) {
        mView = view;
        mModel = new SignInModel();
    }


    public void signIn(String nameOrEmail , String pwd){
        mModel.signIn(nameOrEmail,pwd,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ConfigUtil.SUCCESS_MSG) {
                    mView.onShowSuccess(null);
                }else if(msg.what == AVException.USERNAME_MISSING){
                    mView.onUserNameMissing();
                }else if(msg.what == AVException.EMAIL_NOT_FOUND){
                    mView.onEmailNotFound();
                }else if(msg.what == AVException.USER_DOESNOT_EXIST){
                    mView.onUserNotExist();
                }else {
                    mView.onShowError((String) msg.obj);
                }
            }
        });
    }

}

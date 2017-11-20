package com.chrissen.cartoon.module.presenter.user;

import android.os.Handler;
import android.os.Message;

import com.chrissen.cartoon.module.model.user.RegisterModel;
import com.chrissen.cartoon.module.view.RegisterView;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class RegisterPresenter {
    private RegisterModel mModel;
    private RegisterView mView;

    public RegisterPresenter(RegisterView view) {
        mView = view;
        mModel = new RegisterModel();
    }

    public void register(String userName , String email , String pwd){
        mModel.register(userName,email,pwd,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ConfigUtil.SUCCESS_MSG) {
                    mView.onShowSuccess(null);
                }else if(msg.what == 202){
                    mView.onSameName();
                }else if(msg.what == 203){
                    mView.onEmailRegistered();
                }else {
                    mView.onShowError((String) msg.obj);
                }
            }
        });
    }

}

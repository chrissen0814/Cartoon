package com.chrissen.cartoon.module.presenter.user;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.chrissen.cartoon.module.model.user.ForgetPwdModel;
import com.chrissen.cartoon.module.view.ForgetPwdView;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class ForgetPwdPresenter {

    private ForgetPwdModel mModel;
    private ForgetPwdView mView;

    public ForgetPwdPresenter(ForgetPwdView view) {
        mView = view;
        mModel = new ForgetPwdModel();
    }

    public void forgetPwd(String email){
        mModel.forgetPwd(email,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ConfigUtil.SUCCESS_MSG) {
                    mView.onShowSuccess(null);
                }else if(msg.what == AVException.EMAIL_NOT_FOUND){
                    mView.onEmailNotFound();
                }else{
                    mView.onShowError((String) msg.obj);
                }
            }
        });
    }

}

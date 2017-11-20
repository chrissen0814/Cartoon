package com.chrissen.cartoon.module.model.user;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class SignInModel {


    public void signIn(String nameOrEmail , String pwd , final Handler handler){
        AVUser.logInInBackground(nameOrEmail, pwd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                Message message = new Message();
                if (e == null) {
                    message.what = ConfigUtil.SUCCESS_MSG;
                }else if(e.getCode() == AVException.EMAIL_NOT_FOUND){
                    message.what = AVException.EMAIL_NOT_FOUND;
                }else if(e.getCode() == AVException.USER_DOESNOT_EXIST){
                    message.what = AVException.USER_DOESNOT_EXIST;
                }else if(e.getCode() == AVException.USERNAME_MISSING){
                    message.what = AVException.USERNAME_MISSING;
                }else {
                    message.what = ConfigUtil.FAIL_MSG;
                    message.obj = e.getMessage();
                }
                handler.sendMessage(message);
            }
        });
    }


}

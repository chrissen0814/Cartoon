package com.chrissen.cartoon.module.model.user;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class RegisterModel {

    public void register(String name , String email , String pwd , final Handler handler){
        AVUser user = new AVUser();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(pwd);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                Message message = new Message();
                if (e == null) {
                    //success
                    message.what = ConfigUtil.SUCCESS_MSG;
                }else if(e.getCode() == 202) {
                    //userName same
                    message.what = 202;
                }else if(e.getCode() == 203){
                    //email registered
                    message.what = 203;
                }else {
                    message.what = ConfigUtil.FAIL_MSG;
                    message.obj = e.getMessage();
                }
                handler.sendMessage(message);
            }
        });
    }

}

package com.chrissen.cartoon.module.model.user;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.chrissen.cartoon.util.ConfigUtil;

/**
 * Created by chris on 2017/11/20.
 */

public class ForgetPwdModel {

    public void forgetPwd(String email , final Handler handler){
        AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(AVException e) {
                Message message = new Message();
                if (e == null) {
                    message.what = ConfigUtil.SUCCESS_MSG;
                }else if(e.getCode() == AVException.EMAIL_NOT_FOUND){
                    message.what = AVException.EMAIL_NOT_FOUND;
                }else {
                    message.what = ConfigUtil.FAIL_MSG;
                    message.obj = e.getMessage();
                }
                handler.sendMessage(message);
            }
        });
    }

}

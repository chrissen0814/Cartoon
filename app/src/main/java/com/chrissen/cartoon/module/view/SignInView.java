package com.chrissen.cartoon.module.view;

/**
 * Created by chris on 2017/11/20.
 */

public interface SignInView extends BaseView{


    void onEmailNotFound();

    void onUserNotExist();

    void onUserNameMissing();

    @Override
    void onShowSuccess(Object obj);

    @Override
    void onShowError(String errorMsg);
}

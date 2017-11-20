package com.chrissen.cartoon.module.view;

/**
 * Created by chris on 2017/11/20.
 */

public interface RegisterView extends BaseView {



    void onSameName();

    void onEmailRegistered();

    @Override
    void onShowSuccess(Object obj);

    @Override
    void onShowError(String errorMsg);
}

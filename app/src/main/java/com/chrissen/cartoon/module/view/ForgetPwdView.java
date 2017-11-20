package com.chrissen.cartoon.module.view;

/**
 * Created by chris on 2017/11/20.
 */

public interface ForgetPwdView extends BaseView{

    @Override
    void onShowSuccess(Object obj);

    @Override
    void onShowError(String errorMsg);


}

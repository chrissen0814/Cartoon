package com.chrissen.cartoon.module.view;

/**
 * Created by chris on 2017/11/17.
 */

public interface BaseView<T> {

    void onShowSuccess(T obj);

    void onShowError(String errorMsg);

}

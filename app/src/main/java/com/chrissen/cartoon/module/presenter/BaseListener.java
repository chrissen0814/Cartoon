package com.chrissen.cartoon.module.presenter;

/**
 * Created by chris on 2017/11/17.
 */

public interface BaseListener<T> {

    void onSuccess(T obj);

    void onFail(String errorMsg);

}

package com.chrissen.cartoon.util;

/**
 * Created by chris on 2017/11/15.
 */

public interface NetworkCallback<T> {

    void onSuccess(T obj);
    void onError(int errorCode , String reason);
}

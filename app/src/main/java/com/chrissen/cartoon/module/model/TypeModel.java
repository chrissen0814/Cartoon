package com.chrissen.cartoon.module.model;

import com.chrissen.cartoon.dao.cartoontype.TypeNetDao;
import com.chrissen.cartoon.module.presenter.BaseListener;
import com.chrissen.cartoon.util.NetworkCallback;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public class TypeModel {

    public void getComicType(final BaseListener listener){
        new TypeNetDao().queryCartoonType(new NetworkCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> obj) {
                listener.onSuccess(obj);
            }

            @Override
            public void onError(int errorCode, String reason) {
                listener.onFail(reason);
            }
        });
    }

}

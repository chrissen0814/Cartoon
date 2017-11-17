package com.chrissen.cartoon.module.presenter.type;

import com.chrissen.cartoon.module.presenter.BaseListener;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public interface OnTypeListener extends BaseListener<List<String>> {

    @Override
    void onFail(String errorMsg);

    @Override
    void onSuccess(List<String> obj);
}

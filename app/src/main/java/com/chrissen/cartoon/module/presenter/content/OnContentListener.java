package com.chrissen.cartoon.module.presenter.content;

import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.module.presenter.BaseListener;

/**
 * Created by chris on 2017/11/17.
 */

public interface OnContentListener extends BaseListener<ContentBean> {

    @Override
    void onSuccess(ContentBean obj);

    @Override
    void onFail(String errorMsg);
}

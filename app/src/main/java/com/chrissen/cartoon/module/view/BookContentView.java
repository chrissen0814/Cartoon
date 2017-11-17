package com.chrissen.cartoon.module.view;

import com.chrissen.cartoon.bean.ContentBean;

/**
 * Created by chris on 2017/11/17.
 */

public interface BookContentView extends BaseView<ContentBean> {

    @Override
    void onShowSuccess(ContentBean obj);

    @Override
    void onShowError(String errorMsg);
}

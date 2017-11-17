package com.chrissen.cartoon.module.presenter.book;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.presenter.BaseListener;

/**
 * Created by chris on 2017/11/17.
 */

public interface OnBookListener extends BaseListener<BookBean> {

    @Override
    void onSuccess(BookBean obj);

    @Override
    void onFail(String errorMsg);
}

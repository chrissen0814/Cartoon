package com.chrissen.cartoon.module.presenter.search;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.presenter.BaseListener;

/**
 * Created by chris on 2017/11/21.
 */

public interface OnSearchListener extends BaseListener<BookBean> {

    @Override
    void onSuccess(BookBean obj);

    @Override
    void onFail(String errorMsg);

}

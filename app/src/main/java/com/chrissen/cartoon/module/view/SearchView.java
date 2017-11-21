package com.chrissen.cartoon.module.view;

import com.chrissen.cartoon.bean.BookBean;

/**
 * Created by chris on 2017/11/21.
 */

public interface SearchView extends BaseView<BookBean> {

    @Override
    void onShowSuccess(BookBean obj);

    @Override
    void onShowError(String errorMsg);
}

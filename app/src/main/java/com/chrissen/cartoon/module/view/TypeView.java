package com.chrissen.cartoon.module.view;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public interface TypeView extends BaseView<List<String>> {

    @Override
    void onShowSuccess(List<String> obj);

    @Override
    void onShowError(String errorMsg);
}

package com.chrissen.cartoon.module.presenter;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public interface OnTypeListener {

    void onSuccess(List<String> typeList);
    void onError(String errorMsg);

}

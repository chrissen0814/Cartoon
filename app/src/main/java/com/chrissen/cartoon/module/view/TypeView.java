package com.chrissen.cartoon.module.view;

import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public interface TypeView {

    void onSuccess(List<String> typeList);

    void onError(String errorMsg);

}

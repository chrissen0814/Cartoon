package com.chrissen.cartoon.module.view;

import com.chrissen.cartoon.bean.ChapterBean;

/**
 * Created by chris on 2017/11/17.
 */

public interface BookChapterView extends BaseView<ChapterBean> {

    @Override
    void onShowSuccess(ChapterBean obj);

    void onShowError(String errorMsg);

}

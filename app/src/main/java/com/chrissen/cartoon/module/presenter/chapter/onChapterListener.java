package com.chrissen.cartoon.module.presenter.chapter;

import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.module.presenter.BaseListener;

/**
 * Created by chris on 2017/11/17.
 */

public interface onChapterListener extends BaseListener<ChapterBean> {

    @Override
    void onSuccess(ChapterBean obj);

    @Override
    void onFail(String errorMsg);
}

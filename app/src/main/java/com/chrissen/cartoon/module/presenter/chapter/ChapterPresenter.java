package com.chrissen.cartoon.module.presenter.chapter;

import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.module.model.ChapterModel;
import com.chrissen.cartoon.module.view.BaseView;

/**
 * Created by chris on 2017/11/17.
 */

public class ChapterPresenter implements onChapterListener {

    private BaseView mView;
    private ChapterModel mModel;

    public ChapterPresenter(BaseView view) {
        mView = view;
        mModel = new ChapterModel();
    }

    public void getChapterList(String comicName , int skip){
        mModel.getChapter(comicName,skip,this);
    }

    @Override
    public void onSuccess(ChapterBean obj) {
        mView.onShowSuccess(obj);
    }

    @Override
    public void onFail(String errorMsg) {
        mView.onShowError(errorMsg);
    }
}

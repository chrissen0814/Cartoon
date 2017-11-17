package com.chrissen.cartoon.module.presenter.content;

import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.module.model.ContentModel;
import com.chrissen.cartoon.module.view.BaseView;

/**
 * Created by chris on 2017/11/17.
 */

public class ContentPresenter implements OnContentListener {

    private BaseView mView;
    private ContentModel mModel;

    public ContentPresenter(BaseView view) {
        mView = view;
        mModel = new ContentModel();
    }

    public void getContent(String comicName , int id){
        mModel.getContent(comicName,id,this);
    }

    @Override
    public void onSuccess(ContentBean obj) {
        mView.onShowSuccess(obj);
    }

    @Override
    public void onFail(String errorMsg) {
        mView.onShowError(errorMsg);
    }
}

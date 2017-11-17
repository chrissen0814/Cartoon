package com.chrissen.cartoon.module.presenter.book;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.model.BookModel;
import com.chrissen.cartoon.module.view.BaseView;

/**
 * Created by chris on 2017/11/17.
 */

public class BookPresenter implements OnBookListener {

    private BookModel mModel;
    private BaseView mView;


    public BookPresenter(BaseView view) {
        mView = view;
        mModel = new BookModel();
    }

    public void getBookList(String name , String type , int skip , String finish){
        mModel.getBookList(name,type,skip,finish,this);
    }

    @Override
    public void onSuccess(BookBean obj) {
        mView.onShowSuccess(obj);
    }

    @Override
    public void onFail(String errorMsg) {
        mView.onShowError(errorMsg);
    }
}

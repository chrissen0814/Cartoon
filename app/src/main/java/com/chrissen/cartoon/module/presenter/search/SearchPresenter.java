package com.chrissen.cartoon.module.presenter.search;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.module.model.SearchModel;
import com.chrissen.cartoon.module.view.SearchView;

/**
 * Created by chris on 2017/11/21.
 */

public class SearchPresenter implements OnSearchListener {
    private SearchModel mModel;
    private SearchView mView;

    public SearchPresenter(SearchView view) {
        mView = view;
        mModel = new SearchModel();
    }

    public void searchBook(String bookName){
        mModel.searchBook(bookName,this);
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

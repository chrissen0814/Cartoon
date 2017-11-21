package com.chrissen.cartoon.module.model;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.dao.booklist.BookListNetDao;
import com.chrissen.cartoon.module.presenter.search.OnSearchListener;
import com.chrissen.cartoon.util.NetworkCallback;

/**
 * Created by chris on 2017/11/21.
 */

public class SearchModel {

    public void searchBook(String bookName , final OnSearchListener listener){
        new BookListNetDao().searchBook(bookName, new NetworkCallback<BookBean>() {
            @Override
            public void onSuccess(BookBean obj) {
                if (obj != null) {
                    listener.onSuccess(obj);
                }else {
                    listener.onFail("error");
                }
            }

            @Override
            public void onError(int errorCode, String reason) {
                listener.onFail(reason);
            }
        });
    }

}

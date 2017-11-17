package com.chrissen.cartoon.module.model;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.dao.booklist.BookListNetDao;
import com.chrissen.cartoon.module.presenter.BaseListener;
import com.chrissen.cartoon.util.NetworkCallback;

/**
 * Created by chris on 2017/11/17.
 */

public class BookModel {

    public void getBookList(String type , int skip , int finish , final BaseListener listener){
        new BookListNetDao().queryBookList(type, skip, finish, new NetworkCallback<BookBean>() {
            @Override
            public void onSuccess(BookBean obj) {
                listener.onSuccess(obj);
            }

            @Override
            public void onError(int errorCode, String reason) {
                listener.onFail(reason);
            }
        });
    }

}

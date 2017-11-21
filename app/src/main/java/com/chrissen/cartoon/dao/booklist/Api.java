package com.chrissen.cartoon.dao.booklist;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.util.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chris on 2017/11/15.
 */

public interface Api {

    @GET("book")
    Observable<Response<BookBean>> getBookList(@Query("key") String key ,
                                               @Query("type") String type,
                                               @Query("skip") int skip,
                                               @Query("finish") int finish);

    @GET("book")
    Observable<Response<BookBean>> getBookList(@Query("key") String key ,
                                               @Query("type") String type,
                                               @Query("finish") int finish);

    @GET("book")
    Observable<Response<BookBean>> getBookList(@Query("key") String key ,
                                               @Query("name") String bookName);

}

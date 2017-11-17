package com.chrissen.cartoon.dao.chaptercontent;

import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.util.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chris on 2017/11/15.
 */

public interface Api {

    @GET("chapterContent")
    Observable<Response<ContentBean>> getContentByIdAndComicName(@Query("key") String key,
                                                                 @Query("comicName") String comicName,
                                                                 @Query("id") int id);

}

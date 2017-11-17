package com.chrissen.cartoon.dao.chapterlist;

import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.util.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chris on 2017/11/15.
 */

public interface Api {

    @GET("chapter")
    Observable<Response<ChapterBean>> getChapterList(@Query("key") String key,
                                                     @Query("comicName") String comicName,
                                                     @Query("skip") int skip);

}

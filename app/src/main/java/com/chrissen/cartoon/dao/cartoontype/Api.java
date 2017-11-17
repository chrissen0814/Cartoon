package com.chrissen.cartoon.dao.cartoontype;

import com.chrissen.cartoon.util.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chris on 2017/11/15.
 */

public interface Api {

    @GET("category")
    Observable<Response<List<String>>> getCartoonType(@Query("key") String key);

}

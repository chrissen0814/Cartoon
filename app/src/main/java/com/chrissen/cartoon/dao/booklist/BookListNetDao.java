package com.chrissen.cartoon.dao.booklist;

import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.dao.BaseNetworkDao;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.NetworkCallback;
import com.chrissen.cartoon.util.RetrofitUtil;

/**
 * Created by chris on 2017/11/15.
 */

public class BookListNetDao extends BaseNetworkDao<Api> {


    @Override
    protected Api createApi() {
        return RetrofitUtil.newInstance().getApiService(Api.class);
    }


    public void queryBookList(String type , int skip , int finish , NetworkCallback<BookBean> networkCallback){
        if (skip == 0) {
            doRequest(api.getBookList(ConfigUtil.CARTOON_KEY,type,finish),networkCallback);
        }else {
            doRequest(api.getBookList(ConfigUtil.CARTOON_KEY,type,skip,finish),networkCallback);
        }
    }

    public void searchBook(String bookName , NetworkCallback<BookBean> networkCallback){
        doRequest(api.getBookList(ConfigUtil.CARTOON_KEY,bookName) , networkCallback);
    }

}

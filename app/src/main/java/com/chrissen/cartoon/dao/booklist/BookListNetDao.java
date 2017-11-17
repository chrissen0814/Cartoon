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


    public void queryBookList(String bookName , String type , int skip , String finish , NetworkCallback<BookBean> networkCallback){
        doRequest(api.getBookList(ConfigUtil.CARTOON_KEY,bookName,type,skip,finish),networkCallback);
    }

}

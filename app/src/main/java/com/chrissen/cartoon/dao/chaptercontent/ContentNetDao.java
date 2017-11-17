package com.chrissen.cartoon.dao.chaptercontent;

import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.dao.BaseNetworkDao;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.NetworkCallback;
import com.chrissen.cartoon.util.RetrofitUtil;

/**
 * Created by chris on 2017/11/16.
 */

public class ContentNetDao extends BaseNetworkDao<Api> {
    @Override
    protected Api createApi() {
        return RetrofitUtil.newInstance().getApiService(Api.class);
    }


    public void getContentbyIdAndComicName(String comicName , int id , NetworkCallback<ContentBean> networkCallback){
        doRequest(api.getContentByIdAndComicName(ConfigUtil.CARTOON_KEY,comicName,id) , networkCallback);
    }

}

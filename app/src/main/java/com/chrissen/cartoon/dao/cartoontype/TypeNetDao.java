package com.chrissen.cartoon.dao.cartoontype;

import com.chrissen.cartoon.dao.BaseNetworkDao;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.NetworkCallback;
import com.chrissen.cartoon.util.RetrofitUtil;

import java.util.List;

/**
 * Created by chris on 2017/11/15.
 */

public class TypeNetDao extends BaseNetworkDao<Api> {


    @Override
    protected Api createApi() {
        return RetrofitUtil.newInstance().getApiService(Api.class);
    }



    public void queryCartoonType(NetworkCallback<List<String>> networkCallback){
        doRequest(api.getCartoonType(ConfigUtil.CARTOON_KEY) , networkCallback);
    }


}

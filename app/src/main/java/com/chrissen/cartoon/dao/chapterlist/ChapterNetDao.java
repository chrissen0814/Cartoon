package com.chrissen.cartoon.dao.chapterlist;

import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.dao.BaseNetworkDao;
import com.chrissen.cartoon.util.ConfigUtil;
import com.chrissen.cartoon.util.NetworkCallback;
import com.chrissen.cartoon.util.RetrofitUtil;

/**
 * Created by chris on 2017/11/16.
 */

public class ChapterNetDao extends BaseNetworkDao<Api>{

    @Override
    protected Api createApi() {
        return RetrofitUtil.newInstance().getApiService(Api.class);
    }


    public void queryChapterListByComicName(String comicName , int skip , NetworkCallback<ChapterBean> networkCallback){
        doRequest(api.getChapterList(ConfigUtil.CARTOON_KEY,comicName,skip),networkCallback);
    }

}

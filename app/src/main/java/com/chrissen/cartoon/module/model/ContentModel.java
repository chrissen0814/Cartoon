package com.chrissen.cartoon.module.model;

import com.chrissen.cartoon.bean.ContentBean;
import com.chrissen.cartoon.dao.chaptercontent.ContentNetDao;
import com.chrissen.cartoon.module.presenter.BaseListener;
import com.chrissen.cartoon.util.NetworkCallback;

/**
 * Created by chris on 2017/11/17.
 */

public class ContentModel {

    public void getContent(String comicName , int id , final BaseListener listener){
        new ContentNetDao().getContentbyIdAndComicName(comicName, id, new NetworkCallback<ContentBean>() {
            @Override
            public void onSuccess(ContentBean obj) {
                listener.onSuccess(obj);
            }

            @Override
            public void onError(int errorCode, String reason) {
                listener.onFail(reason);
            }
        });
    }

}

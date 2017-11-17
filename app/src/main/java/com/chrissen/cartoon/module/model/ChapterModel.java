package com.chrissen.cartoon.module.model;

import com.chrissen.cartoon.bean.ChapterBean;
import com.chrissen.cartoon.dao.chapterlist.ChapterNetDao;
import com.chrissen.cartoon.module.presenter.BaseListener;
import com.chrissen.cartoon.util.NetworkCallback;

/**
 * Created by chris on 2017/11/17.
 */

public class ChapterModel {

    public void getChapter(String comicName , int skip , final BaseListener listener){
        new ChapterNetDao().queryChapterListByComicName(comicName, skip, new NetworkCallback<ChapterBean>() {
            @Override
            public void onSuccess(ChapterBean obj) {
                listener.onSuccess(obj);
            }

            @Override
            public void onError(int errorCode, String reason) {
                listener.onFail(reason);
            }
        });
    }

}

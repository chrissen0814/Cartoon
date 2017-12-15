package com.chrissen.cartoon.dao.manager;

import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.dao.GreendaoManager;
import com.chrissen.cartoon.dao.greendao.Txt;
import com.chrissen.cartoon.dao.greendao.TxtDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chris on 2017/12/14.
 */

public class TxtDaoManager {

    private TxtDao mTxtDao;

    public TxtDaoManager() {
        mTxtDao = GreendaoManager.getInstance(CartoonApplication.getContext())
                .getDaoSession().getTxtDao();
    }

    public void addTxt(File file){
        long now = Calendar.getInstance().getTimeInMillis();
        Txt txt = new Txt();
        txt.setFilePath(file.getAbsolutePath());
        txt.setName(file.getName().split("\\.")[0]);
        txt.setAddedTime(now);
        txt.setUpdatedTime(now);
        mTxtDao.insert(txt);
    }

    public void deleteTxt(Txt txt){
        mTxtDao.delete(txt);
    }

    public void updateTxt(Txt txt){
        mTxtDao.update(txt);
    }

    public List<Txt> queryAll(){
        QueryBuilder builder = mTxtDao.queryBuilder();
        builder.orderDesc(TxtDao.Properties.UpdatedTime);
        return builder.list();
    }

    public boolean judgeExist(String filePath){
        QueryBuilder builder = mTxtDao.queryBuilder();
        builder.where(TxtDao.Properties.FilePath.eq(filePath));
        List<Txt> txtList = builder.list();
        if (txtList != null && txtList.size() == 1) {
            return true;
        }else {
            return false;
        }

    }

}

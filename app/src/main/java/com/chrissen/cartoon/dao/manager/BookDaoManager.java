package com.chrissen.cartoon.dao.manager;

import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.dao.GreendaoManager;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.greendao.BookDao;

import java.util.List;

/**
 * Created by chris on 2017/11/17.
 */

public class BookDaoManager {

    private BookDao mBookDao;


    public BookDaoManager() {
        mBookDao = GreendaoManager.getInstance(CartoonApplication.getContext())
                .getDaoSession().getBookDao();
    }


    public void addBook(Book book){
        mBookDao.insert(book);
    }

    public void deleteBook(Book book){
        mBookDao.delete(book);
    }

    public void updateBook(Book book){
        mBookDao.update(book);
    }

    public List<Book> queryBook(){
        return mBookDao.loadAll();
    }


}

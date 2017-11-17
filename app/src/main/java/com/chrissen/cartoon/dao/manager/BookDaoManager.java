package com.chrissen.cartoon.dao.manager;

import com.chrissen.cartoon.CartoonApplication;
import com.chrissen.cartoon.bean.BookBean;
import com.chrissen.cartoon.dao.GreendaoManager;
import com.chrissen.cartoon.dao.greendao.Book;
import com.chrissen.cartoon.dao.greendao.BookDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;
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


    public void addBook(BookBean.Book bookBean){
        long now = Calendar.getInstance().getTimeInMillis();
        Book book = new Book();
        book.setBookName(bookBean.getName());
        book.setType(bookBean.getType());
        book.setImageId(bookBean.getCoverImg());
        book.setLastUpdate(bookBean.getLastUpdate());
        book.setArea(bookBean.getArea());
        book.setAddedTime(now);
        book.setUpdatedTime(now);
        book.setFinish(bookBean.isFinish());
        mBookDao.insert(book);
    }

    public void deleteBook(Book book){
        mBookDao.delete(book);
    }

    public void updateBook(Book book){
        mBookDao.update(book);
    }

    public List<Book> queryAllBook(){
        return mBookDao.loadAll();
    }

    public Book queryBookByBean(BookBean.Book bookBean){
        QueryBuilder builder = mBookDao.queryBuilder();
        builder.where(BookDao.Properties.BookName.eq(bookBean.getName()));
        builder.and(BookDao.Properties.Type.eq(bookBean.getType()) , BookDao.Properties.Area.eq(bookBean.getArea()));
        List<Book> bookList = builder.list();
        if (bookList != null && bookList.size() > 0) {
            return bookList.get(0);
        }else {
            return null;
        }
    }

    public boolean judgeExist(String type , String name , String area){
        QueryBuilder builder = mBookDao.queryBuilder();
        builder.where(BookDao.Properties.BookName.eq(name));
        builder.and(BookDao.Properties.Type.eq(type) , BookDao.Properties.Area.eq(area));
        List<Book> bookList = builder.list();

        if (bookList != null && bookList.size() == 1) {
            return true;
        }else {
            return false;
        }

    }


}

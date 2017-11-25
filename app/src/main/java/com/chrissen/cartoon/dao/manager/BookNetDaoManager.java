package com.chrissen.cartoon.dao.manager;

import android.os.Handler;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.chrissen.cartoon.dao.greendao.Book;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chris on 2017/11/24.
 */

public class BookNetDaoManager {

    private static final String CLASS_NAME = "Book";

    private static final String ID = "id";
    private static final String BOOK_ID = "bookId";
    private static final String NAME = "name";
    private static final String COVER_ID = "coverId";
    private static final String TYPE = "type";
    private static final String AREA = "area";
    private static final String FINISH = "finish";
    private static final String LAST_UPDATE = "lastUpdate";
    private static final String ADDED_TIME = "addedTime";
    private static final String UPDATED_TIME = "updatedTime";
    private static final String CHAPTER_NAME = "chapterName";
    private static final String CHAPTER_ID = "chapterId";
    private static final String IMAGE_INDEX = "imageIndex";
    private static final String COMMENT = "comment";
    private static final String USER = "user";

    public static void saveBook(final Book book , final Handler handler){
        final AVObject avObject = new AVObject(CLASS_NAME);
        avObject.put(USER, AVUser.getCurrentUser());
        avObject.put(ID,book.getId());
        avObject.put(NAME,book.getBookName());
        avObject.put(COVER_ID,book.getImageId());
        avObject.put(TYPE,book.getType());
        avObject.put(AREA,book.getArea());
        avObject.put(FINISH,book.getFinish());
//        书籍更新时间
        avObject.put(LAST_UPDATE,book.getLastUpdate());
        avObject.put(ADDED_TIME,book.getAddedTime());
//        读者看书的更新时间
        avObject.put(UPDATED_TIME,book.getUpdatedTime());
        avObject.put(CHAPTER_NAME,book.getChapterName());
        avObject.put(CHAPTER_ID,book.getChapterId());
        avObject.put(IMAGE_INDEX,book.getImageIndex());
        avObject.put(COMMENT,book.getComment());
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    book.setObjectId(avObject.getObjectId());
                    new BookDaoManager().updateBook(book);
                }
            }
        });
    }


    public static void copyBookToDB(){
        AVQuery<AVObject> avQuery = new AVQuery<>(CLASS_NAME);
        try {
           List<AVObject> objectList = avQuery.find();
            io.reactivex.Observable.fromIterable(objectList)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<AVObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(AVObject value) {
                            Book book = new Book();
                            book.setId(value.getLong(ID));
                            book.setObjectId(value.getString(AVObject.OBJECT_ID));
                            book.setBookName(value.getString(NAME));
                            book.setChapterId(value.getString(CHAPTER_ID));
                            book.setChapterName(value.getString(CHAPTER_NAME));
                            book.setImageId(value.getString(COVER_ID));
                            book.setImageIndex(value.getInt(IMAGE_INDEX));
                            book.setType(value.getString(TYPE));
                            book.setArea(value.getString(AREA));
                            book.setFinish(value.getBoolean(FINISH));
                            book.setLastUpdate(value.getString(LAST_UPDATE));
                            book.setAddedTime(value.getLong(ADDED_TIME));
                            book.setUpdatedTime(value.getLong(UPDATED_TIME));
                            book.setComment(value.getString(COMMENT));
                            boolean exist = new BookDaoManager().judgeExist(book.getType(),book.getBookName(),book.getArea());
                            if (!exist) {
                                new BookDaoManager().saveBook(book);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Book book){
        AVObject avObject = AVObject.createWithoutData(CLASS_NAME,book.getObjectId());
        avObject.put(CHAPTER_NAME,book.getChapterName());
        avObject.put(CHAPTER_ID,book.getChapterId());
        avObject.put(IMAGE_INDEX,book.getImageIndex());
        avObject.put(UPDATED_TIME,book.getUpdatedTime());
        avObject.put(COMMENT,book.getComment());
        avObject.saveInBackground();
    }

    public static void deleteBook(Book book){
        AVObject avObject = AVObject.createWithoutData(CLASS_NAME,book.getObjectId());
        avObject.deleteInBackground();
    }



}

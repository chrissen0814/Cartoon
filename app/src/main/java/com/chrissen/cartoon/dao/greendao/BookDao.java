package com.chrissen.cartoon.dao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK".
*/
public class BookDao extends AbstractDao<Book, Long> {

    public static final String TABLENAME = "BOOK";

    /**
     * Properties of entity Book.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ObjectId = new Property(1, String.class, "objectId", false, "OBJECT_ID");
        public final static Property UserEmail = new Property(2, String.class, "userEmail", false, "USER_EMAIL");
        public final static Property BookName = new Property(3, String.class, "bookName", false, "BOOK_NAME");
        public final static Property ChapterId = new Property(4, String.class, "chapterId", false, "CHAPTER_ID");
        public final static Property ChapterName = new Property(5, String.class, "chapterName", false, "CHAPTER_NAME");
        public final static Property ImageId = new Property(6, String.class, "imageId", false, "IMAGE_ID");
        public final static Property ImageIndex = new Property(7, long.class, "imageIndex", false, "IMAGE_INDEX");
        public final static Property Type = new Property(8, String.class, "type", false, "TYPE");
        public final static Property Area = new Property(9, String.class, "area", false, "AREA");
        public final static Property Finish = new Property(10, boolean.class, "finish", false, "FINISH");
        public final static Property LastUpdate = new Property(11, String.class, "lastUpdate", false, "LAST_UPDATE");
        public final static Property AddedTime = new Property(12, long.class, "addedTime", false, "ADDED_TIME");
        public final static Property UpdatedTime = new Property(13, long.class, "updatedTime", false, "UPDATED_TIME");
        public final static Property Comment = new Property(14, String.class, "comment", false, "COMMENT");
    }


    public BookDao(DaoConfig config) {
        super(config);
    }
    
    public BookDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"OBJECT_ID\" TEXT," + // 1: objectId
                "\"USER_EMAIL\" TEXT," + // 2: userEmail
                "\"BOOK_NAME\" TEXT," + // 3: bookName
                "\"CHAPTER_ID\" TEXT," + // 4: chapterId
                "\"CHAPTER_NAME\" TEXT," + // 5: chapterName
                "\"IMAGE_ID\" TEXT," + // 6: imageId
                "\"IMAGE_INDEX\" INTEGER NOT NULL ," + // 7: imageIndex
                "\"TYPE\" TEXT," + // 8: type
                "\"AREA\" TEXT," + // 9: area
                "\"FINISH\" INTEGER NOT NULL ," + // 10: finish
                "\"LAST_UPDATE\" TEXT," + // 11: lastUpdate
                "\"ADDED_TIME\" INTEGER NOT NULL ," + // 12: addedTime
                "\"UPDATED_TIME\" INTEGER NOT NULL ," + // 13: updatedTime
                "\"COMMENT\" TEXT);"); // 14: comment
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindString(2, objectId);
        }
 
        String userEmail = entity.getUserEmail();
        if (userEmail != null) {
            stmt.bindString(3, userEmail);
        }
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(4, bookName);
        }
 
        String chapterId = entity.getChapterId();
        if (chapterId != null) {
            stmt.bindString(5, chapterId);
        }
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(6, chapterName);
        }
 
        String imageId = entity.getImageId();
        if (imageId != null) {
            stmt.bindString(7, imageId);
        }
        stmt.bindLong(8, entity.getImageIndex());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(10, area);
        }
        stmt.bindLong(11, entity.getFinish() ? 1L: 0L);
 
        String lastUpdate = entity.getLastUpdate();
        if (lastUpdate != null) {
            stmt.bindString(12, lastUpdate);
        }
        stmt.bindLong(13, entity.getAddedTime());
        stmt.bindLong(14, entity.getUpdatedTime());
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(15, comment);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindString(2, objectId);
        }
 
        String userEmail = entity.getUserEmail();
        if (userEmail != null) {
            stmt.bindString(3, userEmail);
        }
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(4, bookName);
        }
 
        String chapterId = entity.getChapterId();
        if (chapterId != null) {
            stmt.bindString(5, chapterId);
        }
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(6, chapterName);
        }
 
        String imageId = entity.getImageId();
        if (imageId != null) {
            stmt.bindString(7, imageId);
        }
        stmt.bindLong(8, entity.getImageIndex());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(10, area);
        }
        stmt.bindLong(11, entity.getFinish() ? 1L: 0L);
 
        String lastUpdate = entity.getLastUpdate();
        if (lastUpdate != null) {
            stmt.bindString(12, lastUpdate);
        }
        stmt.bindLong(13, entity.getAddedTime());
        stmt.bindLong(14, entity.getUpdatedTime());
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(15, comment);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Book readEntity(Cursor cursor, int offset) {
        Book entity = new Book( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // objectId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userEmail
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // bookName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // chapterId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // chapterName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // imageId
            cursor.getLong(offset + 7), // imageIndex
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // type
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // area
            cursor.getShort(offset + 10) != 0, // finish
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // lastUpdate
            cursor.getLong(offset + 12), // addedTime
            cursor.getLong(offset + 13), // updatedTime
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // comment
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Book entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setObjectId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserEmail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBookName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setChapterId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setChapterName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setImageId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setImageIndex(cursor.getLong(offset + 7));
        entity.setType(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setArea(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setFinish(cursor.getShort(offset + 10) != 0);
        entity.setLastUpdate(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setAddedTime(cursor.getLong(offset + 12));
        entity.setUpdatedTime(cursor.getLong(offset + 13));
        entity.setComment(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Book entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Book entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Book entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

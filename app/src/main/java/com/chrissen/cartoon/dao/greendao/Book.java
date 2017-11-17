package com.chrissen.cartoon.dao.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by chris on 2017/11/16.
 */

@Entity
public class Book {

    @Id(autoincrement = true)
    private Long id;
    private String userEmail;
    private String bookName;
    private String chapterId;
    private String chapterName;
    private String imageId;
    private String type;
    private String area;
    private boolean finish;
    private String lastUpdate;
    private long addedTime;
    private long updatedTime;
    private String comment;



    @Generated(hash = 381347066)
    public Book(Long id, String userEmail, String bookName, String chapterId,
            String chapterName, String imageId, String type, String area,
            boolean finish, String lastUpdate, long addedTime, long updatedTime,
            String comment) {
        this.id = id;
        this.userEmail = userEmail;
        this.bookName = bookName;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.imageId = imageId;
        this.type = type;
        this.area = area;
        this.finish = finish;
        this.lastUpdate = lastUpdate;
        this.addedTime = addedTime;
        this.updatedTime = updatedTime;
        this.comment = comment;
    }

    @Generated(hash = 1839243756)
    public Book() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public boolean getFinish() {
        return this.finish;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }
}

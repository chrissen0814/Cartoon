package com.chrissen.cartoon.dao.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by chris on 2017/12/14.
 */

@Entity
public class Txt implements Serializable {

    private static final long serialVersionUID = 123L;

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String chapterName;
    private String filePath;
    private float progress;
    private long addedTime;
    private long updatedTime;
    private int charIndex;
    private int currentCharIndex;
    private int currentParagraphIndex;


    @Generated(hash = 641471378)
    public Txt() {
    }


    @Generated(hash = 237235526)
    public Txt(Long id, String name, String chapterName, String filePath,
            float progress, long addedTime, long updatedTime, int charIndex,
            int currentCharIndex, int currentParagraphIndex) {
        this.id = id;
        this.name = name;
        this.chapterName = chapterName;
        this.filePath = filePath;
        this.progress = progress;
        this.addedTime = addedTime;
        this.updatedTime = updatedTime;
        this.charIndex = charIndex;
        this.currentCharIndex = currentCharIndex;
        this.currentParagraphIndex = currentParagraphIndex;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }


    public int getCharIndex() {
        return charIndex;
    }

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getCurrentCharIndex() {
        return currentCharIndex;
    }

    public void setCurrentCharIndex(int currentCharIndex) {
        this.currentCharIndex = currentCharIndex;
    }

    public int getCurrentParagraphIndex() {
        return currentParagraphIndex;
    }

    public void setCurrentParagraphIndex(int currentParagraphIndex) {
        this.currentParagraphIndex = currentParagraphIndex;
    }
}

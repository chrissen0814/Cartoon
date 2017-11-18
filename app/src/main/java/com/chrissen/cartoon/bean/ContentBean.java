package com.chrissen.cartoon.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chris on 2017/11/16.
 */

public class ContentBean {

    private List<Image> imageList;

    public ContentBean() {
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public static class Image implements Serializable{
        private String imageUrl;
        private String id;


        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }


}

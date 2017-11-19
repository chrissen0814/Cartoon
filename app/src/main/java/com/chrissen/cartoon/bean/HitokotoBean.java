package com.chrissen.cartoon.bean;

/**
 * Created by chris on 2017/11/19.
 */

public class HitokotoBean {

    private String id;
//    content
    private String hitokoto;
    private String from;
    private String created_at;

    public HitokotoBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

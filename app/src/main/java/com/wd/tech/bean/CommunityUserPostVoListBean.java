package com.wd.tech.bean;

/**
 * date: 2019/2/20.
 * Created 王思敏
 * function:
 */
public class CommunityUserPostVoListBean {
    /**
     * comment : 0
     * content : 哈喽
     * file :
     * id : 418
     * praise : 1
     * whetherGreat : 2
     */

    private int comment;
    private String content;
    private String file;
    private int id;
    private int praise;
    private int whetherGreat;

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getWhetherGreat() {
        return whetherGreat;
    }

    public void setWhetherGreat(int whetherGreat) {
        this.whetherGreat = whetherGreat;
    }
}

package com.wd.tech.bean;

/**
 * date: 2019/2/21.
 * Created 王思敏
 * function:社区评论
 */
public class communityCommentVoList {
    private int userId;
    private String nickName;
    private String content;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

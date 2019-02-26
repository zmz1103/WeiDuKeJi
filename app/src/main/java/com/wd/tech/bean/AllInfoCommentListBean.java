package com.wd.tech.bean;

/**
 * date:2019/2/26
 * author:李阔(淡意衬优柔)
 * function:
 */
public class AllInfoCommentListBean {

    /**
     * commentTime : 1551164505000
     * content : oni
     * headPic : http://172.17.8.100/images/tech/default/tech.jpg
     * id : 20
     * infoId : 52
     * nickName : 泰迪
     * userId : 4
     */

    private long commentTime;
    private String content;
    private String headPic;
    private int id;
    private int infoId;
    private String nickName;
    private int userId;

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

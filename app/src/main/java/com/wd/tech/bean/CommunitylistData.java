package com.wd.tech.bean;

import java.util.List;

/**
 * date: 2019/2/19.
 * Created 王思敏
 * function:社区列表
 */
public class CommunitylistData {

    /**
     * comment : 1
     * communityCommentVoList : [{"content":"111","nickName":"999","userId":23}]
     * content : 1
     * file :
     * headPic : http://172.17.8.100/images/tech/head_pic/2019-02-26/20190226203514.png
     * id : 26
     * nickName : 999
     * praise : 0
     * publishTime : 1551141640000
     * signature : 在座的都是弟弟
     * userId : 23
     * whetherFollow : 2
     * whetherGreat : 2
     * whetherVip : 2
     */

    private int comment;
    private String content;
    private String file;
    private String headPic;
    private int id;
    private String nickName;
    private int praise;
    private long publishTime;
    private String signature;
    private int userId;
    private int whetherFollow;
    private int whetherGreat;
    private int whetherVip;
    private List<communityCommentVoList> communityCommentVoList;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWhetherFollow() {
        return whetherFollow;
    }

    public void setWhetherFollow(int whetherFollow) {
        this.whetherFollow = whetherFollow;
    }

    public int getWhetherGreat() {
        return whetherGreat;
    }

    public void setWhetherGreat(int whetherGreat) {
        this.whetherGreat = whetherGreat;
    }

    public int getWhetherVip() {
        return whetherVip;
    }

    public void setWhetherVip(int whetherVip) {
        this.whetherVip = whetherVip;
    }

    public List<com.wd.tech.bean.communityCommentVoList> getCommunityCommentVoList() {
        return communityCommentVoList;
    }

    public void setCommunityCommentVoList(List<com.wd.tech.bean.communityCommentVoList> communityCommentVoList) {
        this.communityCommentVoList = communityCommentVoList;
    }
}

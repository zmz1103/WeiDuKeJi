package com.wd.tech.bean;

/**
 * date:2019/2/27 20:16
 * author:赵明珠(啊哈)
 * function:
 */
public class FriendInform {

    private String fromHeadPic;
    private String fromNickName;
    private int fromUid;
    private int noticeId;
    private long noticeTime;
    private int receiveUid;
    private String remark;
    private String nickName;
    private String groupName;
    private String headPic;
    private int type;
    private int status;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getFromHeadPic() {
        return fromHeadPic;
    }

    public void setFromHeadPic(String fromHeadPic) {
        this.fromHeadPic = fromHeadPic;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public long getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(long noticeTime) {
        this.noticeTime = noticeTime;
    }

    public int getReceiveUid() {
        return receiveUid;
    }

    public void setReceiveUid(int receiveUid) {
        this.receiveUid = receiveUid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

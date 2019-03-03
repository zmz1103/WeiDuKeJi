package com.wd.tech.bean;

/**
 * date:2019/3/1 11:35
 * author:赵明珠(啊哈)
 * function:模糊查询
 */
public class FuzzyQuery {
    private int friendUid;
    private String remarkName;
    private String nickName;
    private String headPic;
    private String buddySource;
    private String userName;
    private String pwd;

    public int getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(int friendUid) {
        this.friendUid = friendUid;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getBuddySource() {
        return buddySource;
    }

    public void setBuddySource(String buddySource) {
        this.buddySource = buddySource;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

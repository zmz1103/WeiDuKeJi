package com.wd.tech.bean;

/**
 * 作者: Wang on 2019/2/25 16:40
 * 寄语：加油！相信自己可以！！！
 */


public class AttentionListData {
    private long focusTime;
    private int focusUid;
    private String headPic;
    private String nickName;
    private String signature;
    private int userId;
    private int whetherMutualFollow;
    private int whetherVip;

    public long getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(long focusTime) {
        this.focusTime = focusTime;
    }

    public int getFocusUid() {
        return focusUid;
    }

    public void setFocusUid(int focusUid) {
        this.focusUid = focusUid;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getWhetherMutualFollow() {
        return whetherMutualFollow;
    }

    public void setWhetherMutualFollow(int whetherMutualFollow) {
        this.whetherMutualFollow = whetherMutualFollow;
    }

    public int getWhetherVip() {
        return whetherVip;
    }

    public void setWhetherVip(int whetherVip) {
        this.whetherVip = whetherVip;
    }
}

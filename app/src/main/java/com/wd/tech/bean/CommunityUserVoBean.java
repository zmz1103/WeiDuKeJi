package com.wd.tech.bean;

/**
 * date: 2019/2/20.
 * Created 王思敏
 * function:
 */
public class CommunityUserVoBean {
    /**
     * headPic : http://172.17.8.100/images/tech/default/tech.jpg
     * nickName : Game player
     * userId : 1021
     * whetherFollow : 2
     * whetherMyFriend : 2
     */

    private String headPic;
    private String nickName;
    private String signature;

    private int userId;
    private int whetherFollow;
    private int whetherMyFriend;

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

    public int getWhetherFollow() {
        return whetherFollow;
    }

    public void setWhetherFollow(int whetherFollow) {
        this.whetherFollow = whetherFollow;
    }

    public int getWhetherMyFriend() {
        return whetherMyFriend;
    }

    public void setWhetherMyFriend(int whetherMyFriend) {
        this.whetherMyFriend = whetherMyFriend;
    }
}

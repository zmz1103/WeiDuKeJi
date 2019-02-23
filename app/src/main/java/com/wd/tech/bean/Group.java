package com.wd.tech.bean;

import java.util.List;

/**
 * date:2019/2/20 15:10
 * author:赵明珠(啊哈)
 * function:
 */
public class Group {

    private int currentNumber;
    private int customize;
    private int groupId;
    private String groupName;
    private int black;
    private List<FriendInfoList>friendInfoList;

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public List<FriendInfoList> getFriendInfoList() {
        return friendInfoList;
    }

    public void setFriendInfoList(List<FriendInfoList> friendInfoList) {
        this.friendInfoList = friendInfoList;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getCustomize() {
        return customize;
    }

    public void setCustomize(int customize) {
        this.customize = customize;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

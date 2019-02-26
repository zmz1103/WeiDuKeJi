package com.wd.tech.bean;

/**
 * date: 2019/2/25.
 * Created 王思敏
 * function:创建的分组
 */
public class MyGroupListBean {
    /**
     * blackFlag : 0
     * groupId : 1036
     * groupImage : http://172.17.8.100/images/tech/default/tech.jpg
     * groupName : 13
     * hxGroupId : 62678621028353
     * role : 3
     */

    private int blackFlag;
    private int groupId;
    private String groupImage;
    private String groupName;
    private String hxGroupId;
    private int role;

    public int getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(int blackFlag) {
        this.blackFlag = blackFlag;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHxGroupId() {
        return hxGroupId;
    }

    public void setHxGroupId(String hxGroupId) {
        this.hxGroupId = hxGroupId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

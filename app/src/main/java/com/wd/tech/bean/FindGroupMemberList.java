package com.wd.tech.bean;

/**
 * date: 2019/3/6.
 * Created 王思敏
 * function:查询群组内所有用户信息
 */
public class FindGroupMemberList {
    private int userId;
    private String nickName;
    private String headPic;
    private String role;

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

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

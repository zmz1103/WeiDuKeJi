package com.wd.tech.bean;

import java.util.List;

/**
 * date: 2019/2/25.
 * Created 王思敏
 * function:查询用户信息
 */
public class QueryFriendList {
    /**
     * birthday : 913219200000
     * email : 884923222@qq.com
     * headPic : http://172.17.8.100/images/tech/head_pic/2018-10-16/20181016084640.jpg
     * integral : 90
     * myGroupList : [{"blackFlag":0,"groupId":1036,"groupImage":"http://172.17.8.100/images/tech/default/tech.jpg","groupName":"13","hxGroupId":"62678621028353","role":3}]
     * nickName : 嘉
     * phone : 17710137468
     * sex : 1
     * signature : 嘉的签名
     * userId : 1010
     * whetherVip : 2
     */

    private long birthday;
    private String email;
    private String headPic;
    private int integral;
    private String nickName;
    private String phone;
    private int sex;
    private String signature;
    private int userId;
    private int whetherVip;
    private List<MyGroupListBean> myGroupList;

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public int getWhetherVip() {
        return whetherVip;
    }

    public void setWhetherVip(int whetherVip) {
        this.whetherVip = whetherVip;
    }

    public List<MyGroupListBean> getMyGroupList() {
        return myGroupList;
    }

    public void setMyGroupList(List<MyGroupListBean> myGroupList) {
        this.myGroupList = myGroupList;
    }
}

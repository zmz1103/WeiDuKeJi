package com.wd.tech.bean;

/**
 * 作者: Wang on 2019/2/24 19:00
 * 寄语：加油！相信自己可以！！！
 */


public class GetUserBean {
    private String email;
    private String headPic;
    private int integral;
    private String nickName;
    private String phone;
    private int sex;
    private String signature;
    private int userId;
    private int whetherVip;
    private int whetherFaceId;
    private long birthday;

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getBirthday() {
        return birthday;
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

    public int getWhetherFaceId() {
        return whetherFaceId;
    }

    public void setWhetherFaceId(int whetherFaceId) {
        this.whetherFaceId = whetherFaceId;
    }
}

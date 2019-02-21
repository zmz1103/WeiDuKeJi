package com.wd.tech.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: Wang on 2019/2/19 19:16
 * 寄语：加油！相信自己可以！！！
 */


@Entity()
public class User {
    private String nickName;
    private String phone;
    private String pwd;
    private String sessionId;
    @Id
    private long userId;
    private String userName;
    private int whetherVip;
    private int whetherFaceId;

    private int sole=0;
    private String headPic;



    @Generated(hash = 1952789088)
    public User(String nickName, String phone, String pwd, String sessionId,
            long userId, String userName, int whetherVip, int whetherFaceId,
            int sole, String headPic) {
        this.nickName = nickName;
        this.phone = phone;
        this.pwd = pwd;
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
        this.whetherVip = whetherVip;
        this.whetherFaceId = whetherFaceId;
        this.sole = sole;
        this.headPic = headPic;
    }

    @Generated(hash = 586692638)
    public User() {
    }



    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getHeadPic() {
        return headPic;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getSole() {
        return sole;
    }

    public void setSole(int sole) {
        this.sole = sole;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", whetherVip=" + whetherVip +
                ", whetherFaceId=" + whetherFaceId +
                ", sole=" + sole +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}

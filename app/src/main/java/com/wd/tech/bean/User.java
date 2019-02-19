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
    private String nickname;
    private String phone;
    private String pwd;
    private String sessionid;
    @Id
    private long userid;
    private String username;
    private int whethervip;
    private int whetherfaceid;

    private int sole;

    @Generated(hash = 714595372)
    public User(String nickname, String phone, String pwd, String sessionid,
            long userid, String username, int whethervip, int whetherfaceid,
            int sole) {
        this.nickname = nickname;
        this.phone = phone;
        this.pwd = pwd;
        this.sessionid = sessionid;
        this.userid = userid;
        this.username = username;
        this.whethervip = whethervip;
        this.whetherfaceid = whetherfaceid;
        this.sole = sole;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public void setSole(int sole) {
        this.sole = sole;
    }

    public int getSole() {
        return sole;
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWhethervip() {
        return whethervip;
    }

    public void setWhethervip(int whethervip) {
        this.whethervip = whethervip;
    }

    public int getWhetherfaceid() {
        return whetherfaceid;
    }

    public void setWhetherfaceid(int whetherfaceid) {
        this.whetherfaceid = whetherfaceid;
    }
}

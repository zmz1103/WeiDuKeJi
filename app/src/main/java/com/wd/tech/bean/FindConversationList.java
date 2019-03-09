package com.wd.tech.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * date:2019/3/6 20:37
 * author:赵明珠(啊哈)
 * function:
 */
@Entity
public class FindConversationList {
    @Id
    private long userId;
    private String headPic;
    private String nickName;
    private String pwd;
    private String userName;
    @Generated(hash = 52308441)
    public FindConversationList(long userId, String headPic, String nickName,
            String pwd, String userName) {
        this.userId = userId;
        this.headPic = headPic;
        this.nickName = nickName;
        this.pwd = pwd;
        this.userName = userName;
    }
    @Generated(hash = 1653457)
    public FindConversationList() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

}

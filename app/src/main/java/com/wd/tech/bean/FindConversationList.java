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
    private long id;
    private String headPic;
    private String nickName;
    private String pwd;
    private int userId;
    private String userName;

    @Generated(hash = 998582365)
    public FindConversationList(long id, String headPic, String nickName,
            String pwd, int userId, String userName) {
        this.id = id;
        this.headPic = headPic;
        this.nickName = nickName;
        this.pwd = pwd;
        this.userId = userId;
        this.userName = userName;
    }

    @Generated(hash = 1653457)
    public FindConversationList() {
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

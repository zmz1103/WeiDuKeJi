package com.wd.tech.bean;

/**
 * 作者: Wang on 2019/2/20 10:52
 * 寄语：加油！相信自己可以！！！
 */


public class WxData {
    private String sessionId;

    private int userId;

    private User result;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }
}

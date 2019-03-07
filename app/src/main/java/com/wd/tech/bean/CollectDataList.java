package com.wd.tech.bean;

/**
 * 作者: Wang on 2019/2/25 11:19
 * 寄语：加油！相信自己可以！！！
 */


public class CollectDataList {
    private long createTime;
    private int id;
    private int infoId;
    private String thumbnail;
    private String title;
    private boolean isFlag=false;


    private boolean kan;

    public void setKan(boolean kan) {
        this.kan = kan;
    }

    public boolean isKan() {
        return kan;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.wd.tech.bean;

import java.io.Serializable;

/**
 * date:2019/3/1
 * author:李阔(淡意衬优柔)
 * function:
 */
public class Transfer implements Serializable {
    private String title;
    private String neirong;
    private String laiyuan;
    private String tupian;
    private long time;
    private int shoucang;
    private int shoucangshu;
    private int shareshu;

    public int getShoucangshu() {
        return shoucangshu;
    }

    public void setShoucangshu(int shoucangshu) {
        this.shoucangshu = shoucangshu;
    }

    public int getShareshu() {
        return shareshu;
    }

    public void setShareshu(int shareshu) {
        this.shareshu = shareshu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getLaiyuan() {
        return laiyuan;
    }

    public void setLaiyuan(String laiyuan) {
        this.laiyuan = laiyuan;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getShoucang() {
        return shoucang;
    }

    public void setShoucang(int shoucang) {
        this.shoucang = shoucang;
    }
}

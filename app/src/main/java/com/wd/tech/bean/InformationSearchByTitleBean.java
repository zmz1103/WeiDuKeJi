package com.wd.tech.bean;

/**
 * date:2019/2/24
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InformationSearchByTitleBean {

    /**
     * id : 50
     * releaseTime : 1539582903000
     * source : 全天候科技
     * title : 行业薪酬“大跳水”，区块链真的凉了？
     */

    private int id;
    private long releaseTime;
    private String source;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.wd.tech.bean;

/**
 * 作者: Wang on 2019/2/28 14:04
 * 寄语：加油！相信自己可以！！！
 */


public class TaskListData {
    private int status;
    private int taskId;
    private int taskIntegral;
    private String taskName;
    private int taskType;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskIntegral() {
        return taskIntegral;
    }

    public void setTaskIntegral(int taskIntegral) {
        this.taskIntegral = taskIntegral;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}

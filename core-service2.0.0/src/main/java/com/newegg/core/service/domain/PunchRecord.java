package com.newegg.core.service.domain;

import java.util.Date;

/**
 * by:qin
 * 打卡记录
 */
public class PunchRecord {
    private Date date;//日期
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private int hours;//时数
    private String clockTime;//打卡时间（值为【未完成】或者【2018-8-21 15:28:10】）

    public PunchRecord() {
    }

    public PunchRecord(Date date, Date startTime, Date endTime, int hours, String clockTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hours = hours;
        this.clockTime = clockTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }
}

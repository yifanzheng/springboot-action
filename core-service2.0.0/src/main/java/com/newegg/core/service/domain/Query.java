package com.newegg.core.service.domain;

import java.util.Date;

/**
 * Harry
 * 多条件查询的查询条件Query
 **/
public class Query {
    private Integer page;//页码
    private Integer limit; //每页限制显示记录条数
    private String recordType;//单据类型
    private Date startTime;//查询开始时间
    private Date endTime;//查询结束时间
    private String approvalStatus;//审批状态
    private Integer attachmentStatus;//有无附件
    private String department;//部门
    private String shift; // 班次
    private Integer hour;//时数
    private String name;//员工姓名

    public Query(){}

    public Query(Integer page, Integer limit, String recordType, Date startTime, Date endTime, String approvalStatus, Integer attachmentStatus, String department, String shift, Integer hour, String name) {
        this.page = page;
        this.limit = limit;
        this.recordType = recordType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.approvalStatus = approvalStatus;
        this.attachmentStatus = attachmentStatus;
        this.department = department;
        this.shift = shift;
        this.hour = hour;
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getAttachmentStatus() {
        return attachmentStatus;
    }

    public void setAttachmentStatus(Integer attachmentStatus) {
        this.attachmentStatus = attachmentStatus;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

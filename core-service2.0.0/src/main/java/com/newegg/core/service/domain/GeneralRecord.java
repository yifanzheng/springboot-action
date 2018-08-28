package com.newegg.core.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * loda
 */

// 一般单据
public class GeneralRecord extends Record{
    private String id;
    private String applicant;
    private String applicantEnglish;
    private String documentType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private Integer hours;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startPunchtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endPunchtime;
    private String shift;
    private String status;
    private String attachment;

    private  Integer sumtime;

    private String dealType="待审批";

    private String problemReason;

    private String department;

    public GeneralRecord() {
    }

    public GeneralRecord(String id, String applicant, String applicantEnglish, String documentType, Date startTime, Date endTime, Integer hours, Date startPunchtime, Date endPunchtime, String shift, String status, String attachment, Integer sumtime, String dealType, String problemReason, String department) {
        this.id = id;
        this.applicant = applicant;
        this.applicantEnglish = applicantEnglish;
        this.documentType = documentType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hours = hours;
        this.startPunchtime = startPunchtime;
        this.endPunchtime = endPunchtime;
        this.shift = shift;
        this.status = status;
        this.attachment = attachment;
        this.sumtime = sumtime;
        this.dealType = dealType;
        this.problemReason = problemReason;
        this.department = department;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicantEnglish() {
        return applicantEnglish;
    }

    public void setApplicantEnglish(String applicantEnglish) {
        this.applicantEnglish = applicantEnglish;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Date getStartPunchtime() {
        return startPunchtime;
    }

    public void setStartPunchtime(Date startPunchtime) {
        this.startPunchtime = startPunchtime;
    }

    public Date getEndPunchtime() {
        return endPunchtime;
    }

    public void setEndPunchtime(Date endPunchtime) {
        this.endPunchtime = endPunchtime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getSumtime() {
        return sumtime;
    }

    public void setSumtime(Integer sumtime) {
        this.sumtime = sumtime;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getProblemReason() {
        return problemReason;
    }

    public void setProblemReason(String problemReason) {
        this.problemReason = problemReason;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

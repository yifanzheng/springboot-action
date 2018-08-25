package com.newegg.core.service.domain;

import java.util.Date;
import java.util.List;

/**
 * loda
 */

// 签卡单据
public class ClockRecord extends Record{
    private String id;
    private String applicant;
    private String applicantEnglish;
    private String department;
    private String documentType;
    private List<PunchRecord> punchRecordList;
    private Date applyDate;
    private String  clockReason;

    private String status;//这个值是从数据库拿过来的，只能是【已审批】，【待审批】

    private String clockAattachment;

    private String dealType="待审批";//这个值是我们预处理的结果，【待审批】，【预处理通过】，【预处理未通过】、、、

    private String problemReason;

    public ClockRecord() {
    }

    public ClockRecord(String id, String applicant, String applicantEnglish, String department, String documentType, List<PunchRecord> punchRecordList, Date applyDate, String clockReason, String status, String clockAattachment, String dealType, String problemReason) {
        this.id = id;
        this.applicant = applicant;
        this.applicantEnglish = applicantEnglish;
        this.department = department;
        this.documentType = documentType;
        this.punchRecordList = punchRecordList;
        this.applyDate = applyDate;
        this.clockReason = clockReason;
        this.status = status;
        this.clockAattachment = clockAattachment;
        this.dealType = dealType;
        this.problemReason = problemReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getClockReason() {
        return clockReason;
    }

    public void setClockReason(String clockReason) {
        this.clockReason = clockReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClockAattachment() {
        return clockAattachment;
    }

    public void setClockAattachment(String clockAattachment) {

        this.clockAattachment = clockAattachment;
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

    public List<PunchRecord> getPunchRecordList() {
        return punchRecordList;
    }

    public void setPunchRecordList(List<PunchRecord> punchRecordList) {
        this.punchRecordList = punchRecordList;
    }

    public String getApplicantEnglish() {
        return applicantEnglish;
    }

    public void setApplicantEnglish(String applicantEnglish) {
        this.applicantEnglish = applicantEnglish;
    }
}

package com.newegg.core.service.domain;

import java.util.ArrayList;

/**
 * by:qin
 * 审批详情
 */
public class ApprovalDetail {
    private String recordType; //单据类型
    private String submitInfo; //提交信息
    private String applicant; //申请人
    private ArrayList<PunchRecord> detail;//详细信息
    private String remark;//备注
    private ArrayList<ApprovalProcess> approvalProcess;//审批流程
    private String approvalSuggestion;//审批意见
    private String atachment;//附件

    public ApprovalDetail() {
    }

    public ApprovalDetail(String recordType, String submitInfo, String applicant, ArrayList<PunchRecord> detail, String remark, ArrayList<ApprovalProcess> approvalProcess, String approvalSuggestion, String atachment) {
        this.recordType = recordType;
        this.submitInfo = submitInfo;
        this.applicant = applicant;
        this.detail = detail;
        this.remark = remark;
        this.approvalProcess = approvalProcess;
        this.approvalSuggestion = approvalSuggestion;
        this.atachment = atachment;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getSubmitInfo() {
        return submitInfo;
    }

    public void setSubmitInfo(String submitInfo) {
        this.submitInfo = submitInfo;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public ArrayList<PunchRecord> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<PunchRecord> detail) {
        this.detail = detail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ArrayList<ApprovalProcess> getApprovalProcess() {
        return approvalProcess;
    }

    public void setApprovalProcess(ArrayList<ApprovalProcess> approvalProcess) {
        this.approvalProcess = approvalProcess;
    }

    public String getApprovalSuggestion() {
        return approvalSuggestion;
    }

    public void setApprovalSuggestion(String approvalSuggestion) {
        this.approvalSuggestion = approvalSuggestion;
    }

    public String getAtachment() {
        return atachment;
    }

    public void setAtachment(String atachment) {
        this.atachment = atachment;
    }
}

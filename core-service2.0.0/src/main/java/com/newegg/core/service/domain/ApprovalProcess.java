package com.newegg.core.service.domain;

/**
 * @Auther: sz7v
 * @Date: 2018/8/21 13:52
 * @Description: 审批流程对象
 */
public class ApprovalProcess {

    private String approver; //审批人
    private String approvalStatus; //审批状态
    private String approvalTime; // 审批时间
    private String approvalSuggestion; // 审批意见

    public ApprovalProcess() {
    }

    public ApprovalProcess(String approver, String approvalStatus, String approvalTime, String approvalSuggestion) {
        this.approver = approver;
        this.approvalStatus = approvalStatus;
        this.approvalTime = approvalTime;
        this.approvalSuggestion = approvalSuggestion;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalSuggestion() {
        return approvalSuggestion;
    }

    public void setApprovalSuggestion(String approvalSuggestion) {
        this.approvalSuggestion = approvalSuggestion;
    }
}

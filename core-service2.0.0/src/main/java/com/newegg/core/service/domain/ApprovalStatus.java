package com.newegg.core.service.domain;

public class ApprovalStatus {

    private Integer id; // 单据id
    private String status; // 审批状态
    private String approvalSuggestion; //审批意见

    public ApprovalStatus() {
    }

    public ApprovalStatus(Integer id, String status, String approvalSuggestion) {
        this.id = id;
        this.status = status;
        this.approvalSuggestion = approvalSuggestion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovalSuggestion() {
        return approvalSuggestion;
    }

    public void setApprovalSuggestion(String approvalSuggestion) {
        this.approvalSuggestion = approvalSuggestion;
    }


}

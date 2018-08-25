package com.newegg.core.service.domain;

import java.util.ArrayList;

public class ProblemRecord extends Record{
    private String id;
    private String applicant;
    private String applicantEnglish;
    private String department;
    private String documentType;
    private String failureReason;//失败原因
    private String status;
    private String atachment;

    public ProblemRecord() {
    }

    public ProblemRecord(String id, String applicant, String applicantEnglish, String department, String documentType, String failureReason, String status, String atachment) {
        this.id = id;
        this.applicant = applicant;
        this.applicantEnglish = applicantEnglish;
        this.department = department;
        this.documentType = documentType;
        this.failureReason = failureReason;
        this.status = status;
        this.atachment = atachment;
    }

    //合并有问题的单据
    public static ArrayList<ProblemRecord> merge(ArrayList<ClockRecord> clockRecords, ArrayList<GeneralRecord> generalRecords) {
        ArrayList<ProblemRecord> problemRecords=new ArrayList<>();
        clockRecords.forEach(e->{
            ProblemRecord problemRecord=new ProblemRecord();
            problemRecord.setId(e.getId());
            problemRecord.setApplicant(e.getApplicant());
            problemRecord.setDepartment(e.getDepartment());
            problemRecord.setDocumentType(e.getDocumentType());
            problemRecord.setFailureReason(e.getProblemReason());
            problemRecord.setStatus(e.getDealType());
            problemRecord.setAtachment(e.getClockAattachment());
            problemRecords.add(problemRecord);
        });
        generalRecords.forEach(e->{
            ProblemRecord problemRecord=new ProblemRecord();
            problemRecord.setId(e.getId());
            problemRecord.setApplicant(e.getApplicant());
            problemRecord.setDepartment(e.getDepartment());
            problemRecord.setDocumentType(e.getDocumentType());
            problemRecord.setFailureReason(e.getProblemReason());
            problemRecord.setStatus(e.getDealType());
            problemRecord.setAtachment(e.getAttachment());
            problemRecords.add(problemRecord);
        });
        return problemRecords;
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

    public String getApplicantEnglish() {
        return applicantEnglish;
    }

    public void setApplicantEnglish(String applicantEnglish) {
        this.applicantEnglish = applicantEnglish;
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

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAtachment() {
        return atachment;
    }

    public void setAtachment(String atachment) {
        this.atachment = atachment;
    }
}


package com.newegg.core.service.service;

import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;

import java.util.ArrayList;

import java.util.List;


public interface ClockRecordsService {

    void approval(List<ApprovalStatus> approvalStatus);

    void pretreatmentApproval(Query query, List<ClockRecord> list);

    ClockRecord getRecord(String id);

    List<ClockRecord> getRecords(Query query);

    void check(List<ApprovalStatus> approvalStatus);
}

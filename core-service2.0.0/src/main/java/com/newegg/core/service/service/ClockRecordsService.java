package com.newegg.core.service.service;

import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Page;
import com.newegg.core.service.domain.Query;

import java.util.ArrayList;

import java.util.List;


public interface ClockRecordsService {

    void approval(List<ApprovalStatus> approvalStatus);

    void pretreatmentApproval(Query query, ArrayList<ClockRecord> list);

    ClockRecord getRecord(String id);

    List<ClockRecord> getRecords(Query query, Page page);

    void check(List<ApprovalStatus> approvalStatus);
}

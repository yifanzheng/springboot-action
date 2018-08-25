package com.newegg.core.service.service;

import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;

import java.util.List;


public interface GeneralRecordsService {

    void approval(List<ApprovalStatus> approvalStatus);

    GeneralRecord getRecord(String id);

    List<GeneralRecord> getRecords(Query query);

    void check(List<ApprovalStatus> approvalStatus);
}

package com.newegg.core.service.service;

import com.newegg.core.service.domain.Page;
import com.newegg.core.service.domain.ProblemRecord;
import com.newegg.core.service.domain.Query;

import java.util.List;

public interface ProblemRecordService {

    List<ProblemRecord> getRecord(Query query, Page page);
}

package com.newegg.core.service.service;

import com.newegg.core.service.domain.ProblemRecord;
import com.newegg.core.service.domain.Query;

import java.util.ArrayList;

public interface ProblemRecordService {

    ArrayList<ProblemRecord> getRecord(Query query);
}

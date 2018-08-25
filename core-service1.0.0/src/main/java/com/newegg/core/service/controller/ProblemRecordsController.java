package com.newegg.core.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.cache.QinCache;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.ProblemRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecordsRepo;
import com.newegg.core.service.repository.GeneralRecordsRepo;
import com.newegg.core.service.service.ClockRecordsService;
import com.newegg.core.service.service.ProblemRecordService;
import com.newegg.core.service.service.qin.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class ProblemRecordsController {
    @Autowired
    private ProblemRecordService problemRecordService;

    @PostMapping("/problemRecords")
    public ArrayList<ProblemRecord> problemRecords(@RequestBody Query query){
        ArrayList<ProblemRecord> problemRecords= problemRecordService.getRecord(query);
        return problemRecords;
    }
}

package com.newegg.core.service.controller;


import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.service.ClockRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * by:qin
 */
@RestController
public class ClockRecordsController {
    @Autowired
    private ClockRecordsService clockRecordsService;

    @GetMapping("/clockRecords/{id}")
    public ClockRecord clockRecord(@PathVariable String id){
        ClockRecord clockRecord = clockRecordsService.getRecord(id);
        return clockRecord;
    }

    @PostMapping("/clockRecords")
    public List<ClockRecord> clockRecordsPost(@RequestBody Query query){
        List<ClockRecord> clockRecordList = clockRecordsService.getRecords(query);
        return clockRecordList;
    }

    @PutMapping("/clockRecords")
    public void clockRecordsPut(@RequestBody ArrayList<ApprovalStatus> approvalStatus){
        clockRecordsService.approval(approvalStatus);
    }
}

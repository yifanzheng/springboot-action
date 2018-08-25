package com.newegg.core.service.controller;

import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.service.GeneralRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GeneralRecordsController {

    @Autowired
    private GeneralRecordsService generalService;

    /**
     * 匹配/generalRecords/{id}
     * @param id
     * @return 对应id的单据
     */
    @GetMapping("/generalRecords/{id}")
    public GeneralRecord generalRecord(@PathVariable String id) {
        GeneralRecord generalRecord = generalService.getRecord(id);
        return generalRecord;
    }

    @PostMapping("/generalRecords")
    public List<GeneralRecord> generalRecordsPost(@RequestBody Query query) {
        List<GeneralRecord> generalRecordList = generalService.getRecords(query);
        return generalRecordList;
    }

    @PutMapping("/generalRecords}")
    public void generalRecordsPut(@RequestBody ArrayList<ApprovalStatus> approvalStatus) {
        generalService.approval(approvalStatus);
    }
}

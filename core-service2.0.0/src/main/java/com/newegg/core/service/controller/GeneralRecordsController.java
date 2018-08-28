package com.newegg.core.service.controller;

import com.newegg.core.service.domain.*;
import com.newegg.core.service.service.GeneralRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseData generalRecord(@PathVariable String id) {
        GeneralRecord generalRecord = generalService.getRecord(id);
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(),"OK",generalRecord);
        return responseData;
    }

    @PostMapping("/generalRecords")
    public ResponseData generalRecordsPost(@RequestBody Query query) {
        Page page=new Page();
        List<GeneralRecord> generalRecordList = generalService.getRecords(query,page);
        ResponseData responseData = new ResponseData(true,HttpStatus.OK.value(),"OK",generalRecordList,page);
        return responseData;
    }

    @PutMapping("/generalRecords}")
    public void generalRecordsPut(@RequestBody ArrayList<ApprovalStatus> approvalStatus) {
        generalService.approval(approvalStatus);
    }
}

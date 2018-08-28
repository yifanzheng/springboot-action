package com.newegg.core.service.controller;

import com.newegg.core.service.domain.*;
import com.newegg.core.service.service.ClockRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseData clockRecord(@PathVariable String id){
        ClockRecord clockRecord = clockRecordsService.getRecord(id);
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(),"OK",clockRecord);
        return responseData;
    }

    @PostMapping("/clockRecords")
    public ResponseData clockRecordsPost(@RequestBody Query query){
        Page page=new Page();
        List<ClockRecord> returnList= clockRecordsService.getRecords(query,page);
        ResponseData responseData=new ResponseData(true,HttpStatus.OK.value(),"ok",returnList,page);
        return responseData;
    }

    @PutMapping("/clockRecords")
    public void clockRecordsPut(@RequestBody ArrayList<ApprovalStatus> approvalStatus){
        clockRecordsService.approval(approvalStatus);
    }
}

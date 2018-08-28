package com.newegg.core.service.controller;

<<<<<<< HEAD:core-service1.0.0/src/main/java/com/newegg/core/service/controller/ClockRecordsController.java

import com.newegg.core.service.cache.QinCache;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecordsRepo;
=======
import com.newegg.core.service.domain.*;
>>>>>>> a42263d315a06cc0870010fa7c08f83323cc5b63:core-service2.0.0/src/main/java/com/newegg/core/service/controller/ClockRecordsController.java
import com.newegg.core.service.service.ClockRecordsService;
import com.newegg.core.service.service.qin.Leave;
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

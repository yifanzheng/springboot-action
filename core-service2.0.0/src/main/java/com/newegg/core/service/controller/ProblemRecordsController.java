package com.newegg.core.service.controller;

import com.newegg.core.service.domain.Page;
import com.newegg.core.service.domain.ProblemRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.domain.ResponseData;
import com.newegg.core.service.service.ProblemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProblemRecordsController {
    @Autowired
    private ProblemRecordService problemRecordService;

    @PostMapping("/problemRecords")
    public ResponseData problemRecords(@RequestBody Query query){
        Page page=new Page();
        List<ProblemRecord> problemRecords= problemRecordService.getRecord(query,page);
        ResponseData responseData = new ResponseData(true, HttpStatus.OK.value(),"OK",problemRecords,page);
        return responseData;
    }
}

package com.newegg.core.service.repository.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ClockRecordsMock implements ClockRecords {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restfulApi.generalRecordUrl}")
    private String baseUri;

    List<ClockRecord> clockRecordList;

    public ClockRecordsMock() {
        InputStream stream = ClockRecordsMock.class.getResourceAsStream("/mock/clockrecords.json");
        JSONReader reader = new JSONReader(new InputStreamReader(stream));
        clockRecordList = JSON.parseArray(reader.readString(),ClockRecord.class);
    }

    @Override
    public ClockRecord getClockRecord(String id){
        return clockRecordList.get(Integer.parseInt(id));
    }

    @Override
    public List<ClockRecord> getClockRecordsList(Query query){
        return clockRecordList;
    }

    @Override
    public void updateClockRecords(List<ApprovalStatus> approvalStatusList) {

        String requestBody = JSON.toJSONString(approvalStatusList);
        restTemplate.put(baseUri,requestBody);
    }
}

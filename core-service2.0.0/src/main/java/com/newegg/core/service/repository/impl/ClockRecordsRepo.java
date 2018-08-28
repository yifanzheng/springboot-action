package com.newegg.core.service.repository.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class ClockRecordsRepo implements ClockRecords {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restfulApi.generalRecordUrl}")
    private String baseUri;

    @Override
    public ClockRecord getClockRecord(String id){
        String uri = String.format("%s/%s",baseUri,id);
        String responseJson = restTemplate.getForEntity(uri, String.class).getBody();
        ClockRecord clockRecord = JSONObject.parseObject(responseJson, ClockRecord.class);

        return clockRecord;
    }

    @Override
    public List<ClockRecord> getClockRecordsList(Query query){

        String requestBody = JSON.toJSONString(query);

        String responseJson = restTemplate.postForEntity(baseUri,requestBody,String.class).getBody();
        List<ClockRecord> clockRecordsList = JSONObject.parseArray(responseJson,ClockRecord.class);

        return clockRecordsList;
    }

    @Override
    public void updateClockRecords(List<ApprovalStatus> approvalStatusList) {

        String requestBody = JSON.toJSONString(approvalStatusList);
        restTemplate.put(baseUri,requestBody);
    }
}

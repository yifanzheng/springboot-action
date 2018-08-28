package com.newegg.core.service.repository.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.GeneralRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GeneralRecordsRepo implements GeneralRecords {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restfulApi.generalRecordUrl}")
    private String baseUri;


    @Override
    public GeneralRecord getGeneralRecord(String id) {
        String uri = String.format("%s/%s",baseUri,id);
        String responseJson = restTemplate.getForEntity(uri, String.class).getBody();
        GeneralRecord generalRecord = JSONObject.parseObject(responseJson, GeneralRecord.class);

        return generalRecord;
    }


    @Override
    public List<GeneralRecord> getGeneralRecordsList(Query query) {

        String requestBody = JSON.toJSONString(query);

        String responseJson = restTemplate.postForEntity(baseUri,requestBody,String.class).getBody();
        List<GeneralRecord> generalRecordsList = JSONObject.parseArray(responseJson,GeneralRecord.class);

        return generalRecordsList;
    }


    @Override
    public void updateGeneralRecords(List<ApprovalStatus> approvalStatusList) {

        String requestBody = JSON.toJSONString(approvalStatusList);
        restTemplate.put(baseUri,requestBody);
    }

    /**
     * 检查审批后，审批状态是否和预期一致
     * @param approvalStatusList
     */
    private List<GeneralRecord> check(List<ApprovalStatus> approvalStatusList) {
        List<GeneralRecord> sysProblemList = new ArrayList<>();
        for (ApprovalStatus approvalStatus : approvalStatusList) {

            String uri = baseUri + String.format("/%d",approvalStatus.getId());
            GeneralRecord generalRecord = restTemplate.getForEntity(uri, GeneralRecord.class).getBody();
            if (!Objects.equals(generalRecord.getStatus(), approvalStatus.getStatus())) {
                sysProblemList.add(generalRecord);
            }
        }
        return sysProblemList;
    }
}

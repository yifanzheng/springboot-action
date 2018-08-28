package com.newegg.core.service.repository.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONReader;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.GeneralRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.io.FileReader;

@Component
public class GeneralRecordsMock implements GeneralRecords {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restfulApi.generalRecordUrl}")
    private String baseUri;

    List<GeneralRecord> generalRecordList;

    public GeneralRecordsMock() throws FileNotFoundException {
        InputStream stream = GeneralRecordsMock.class.getResourceAsStream("/mock/generalrecords.json");
        JSONReader reader = new JSONReader(new InputStreamReader(stream));
        generalRecordList = JSON.parseArray(reader.readString(),GeneralRecord.class);
    }

    @Override
    public GeneralRecord getGeneralRecord(String id) {
        return generalRecordList.get(Integer.parseInt(id));
    }


    @Override
    public List<GeneralRecord> getGeneralRecordsList(Query query) {
        return generalRecordList;
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

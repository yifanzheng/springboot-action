package com.newegg.core.service.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GeneralRecordsRepo {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restfulApi.generalRecordUrl}")
    private String baseUri;

    /**
     * 访问Restful API,通过id获取单条单据
     * @param id
     * @return 单条单据
     */
    public GeneralRecord getGeneralRecord(String id) {
        String uri = String.format("%s/%s",baseUri,id);
        String responseJson = restTemplate.getForEntity(uri, String.class).getBody();
        GeneralRecord generalRecord = JSONObject.parseObject(responseJson, GeneralRecord.class);

        return generalRecord;
    }

    /**
     * 访问Restful API,通过条件query获取单据列表
     * @param query
     * @return 单据列表
     */
    public List<GeneralRecord> getGeneralRecordsList(Query query) {

        String requestBody = JSON.toJSONString(query);

        String responseJson = restTemplate.postForEntity(baseUri,requestBody,String.class).getBody();
        List<GeneralRecord> generalRecordsList = JSONObject.parseArray(responseJson,GeneralRecord.class);

        return generalRecordsList;
    }

    /**
     * 访问Restful API,更新单据状态
     * @param approvalStatusList
     */
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

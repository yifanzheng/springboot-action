package com.newegg.core.service.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class ClockRecordsRepo {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${restfulApi.generalRecordUrl}")
    private String baseUri;

    /**
     * 访问Restful API,通过id获取单条单据
     * @param id
     * @return 单条单据
     */
    public ClockRecord getClockRecord(String id){
        String uri = String.format("%s/%s",baseUri,id);
        String responseJson = restTemplate.getForEntity(uri, String.class).getBody();
        ClockRecord clockRecord = JSONObject.parseObject(responseJson, ClockRecord.class);

        return clockRecord;
    }

    /**
     * 访问Restful API,通过条件query获取单据列表
     * @param query
     * @return 单据列表
     * @throws URISyntaxException
     */
    public List<ClockRecord> getClockRecordsList(Query query){

        String requestBody = JSON.toJSONString(query);

        String responseJson = restTemplate.postForEntity(baseUri,requestBody,String.class).getBody();
        List<ClockRecord> clockRecordsList = JSONObject.parseArray(responseJson,ClockRecord.class);

        return clockRecordsList;
    }

    /**
     * 访问Restful API,更新单据状态
     * @param approvalStatusList
     */
    public void updateClockRecords(List<ApprovalStatus> approvalStatusList) {

        String requestBody = JSON.toJSONString(approvalStatusList);
        restTemplate.put(baseUri,requestBody);
    }
}

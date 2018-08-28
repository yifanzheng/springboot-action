package com.newegg.core.service.repository;

import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;

import java.net.URISyntaxException;
import java.util.List;

public interface ClockRecords {
    /**
     * 访问Restful API,通过id获取单条单据
     * @param id
     * @return 单条单据
     */
    public ClockRecord getClockRecord(String id);

    /**
     * 访问Restful API,通过条件query获取单据列表
     * @param query
     * @return 单据列表
     * @throws URISyntaxException
     */
    public List<ClockRecord> getClockRecordsList(Query query);

    /**
     * 访问Restful API,更新单据状态
     * @param approvalStatusList
     */
    public void updateClockRecords(List<ApprovalStatus> approvalStatusList);
}

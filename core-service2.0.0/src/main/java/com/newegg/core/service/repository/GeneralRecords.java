package com.newegg.core.service.repository;

import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;

import java.util.List;

public interface GeneralRecords {

    /**
     * 访问Restful API,通过id获取单条单据
     * @param id
     * @return 单条单据
     */
    public GeneralRecord getGeneralRecord(String id);

    /**
     * 访问Restful API,通过条件query获取单据列表
     * @param query
     * @return 单据列表
     */
    public List<GeneralRecord> getGeneralRecordsList(Query query);

    /**
     * 访问Restful API,更新单据状态
     * @param approvalStatusList
     */
    public void updateGeneralRecords(List<ApprovalStatus> approvalStatusList);

}

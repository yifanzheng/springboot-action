package com.newegg.core.service.service.impl;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.cache.QinCache;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.GeneralRecordsRepo;
import com.newegg.core.service.service.GeneralRecordsService;
import com.newegg.core.service.service.qin.chain.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GeneralRecordsServiceImpl implements GeneralRecordsService {

    @Autowired
    private GeneralRecordsRepo generalRecordsRepo;

    @Autowired
    private Entry entry;

    @Autowired
    private QinCache qinCache;


    @Override

    public void approval(List<ApprovalStatus> approvalStatus) {
        generalRecordsRepo.updateGeneralRecords(approvalStatus);
        check(approvalStatus);
    }

    @Override
    public GeneralRecord getRecord(String id) {
        return generalRecordsRepo.getGeneralRecord(id);
    }

    /**
     * 获取单据列表，并交给chain处理
     * @param query
     * @return 处理后的单据
     */
    @Override
    public List<GeneralRecord> getRecords(Query query) {
        List<GeneralRecord> generalRecordList = generalRecordsRepo.getGeneralRecordsList(query);
        for (GeneralRecord generalRecord : generalRecordList) {
            entry.process(generalRecord);
        }
        return generalRecordList;
    }

    @Override
    public void check(List<ApprovalStatus> approvalStatusList) {
        ArrayList<GeneralRecord> sysProblemList = new ArrayList<>();
        for (ApprovalStatus approvalStatus : approvalStatusList) {
            GeneralRecord generalRecord = generalRecordsRepo.getGeneralRecord(String.valueOf(approvalStatus.getId()));
            if (!Objects.equals(generalRecord.getStatus(), approvalStatus.getStatus())) {
                generalRecord.setDealType("系统未通过");
                sysProblemList.add(generalRecord);
            }
        }
        // 放入缓存
        qinCache.saveCache(CacheKey.ProblemRecord,sysProblemList);
    }
}

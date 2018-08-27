package com.newegg.core.service.service.impl;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.cache.QinCache;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecordsRepo;
import com.newegg.core.service.service.ClockRecordsService;
import com.newegg.core.service.service.qin.chain.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ClockRecordsServiceImpl implements ClockRecordsService {
    @Autowired
    private ClockRecordsRepo clockRecordsRepo;

    @Autowired
    private QinCache qinCache;

    @Autowired
    private Entry entry;

    @Override
    public void approval(List<ApprovalStatus> approvalStatus) {
        clockRecordsRepo.updateClockRecords(approvalStatus);
        check(approvalStatus);
    }

    @Override
    public void pretreatmentApproval(Query query, List<ClockRecord> list) {
        for(ClockRecord record:list){
            if(Objects.equals(record.getClockAattachment(),null)
                    ||Objects.equals("",record.getClockAattachment())){
                record.setDealType("预处理不合格");
                record.setProblemReason("没有附件");
            }
        }
    }

    @Override
    public ClockRecord getRecord(String id) {
        ClockRecord clockRecord = clockRecordsRepo.getClockRecord(id);
        return clockRecord;
    }

    @Override
    public List<ClockRecord> getRecords(Query query) {
        List<ClockRecord> clockRecordList = clockRecordsRepo.getClockRecordsList(query);
        //执行预处理
        pretreatmentApproval(query,clockRecordList);
        return clockRecordList;
    }

    @Override
    public void check(List<ApprovalStatus> approvalStatusList) {
        ArrayList<ClockRecord> sysProblemList = new ArrayList<>();
        for (ApprovalStatus approvalStatus : approvalStatusList) {
            ClockRecord clockRecord = clockRecordsRepo.getClockRecord(String.valueOf(approvalStatus.getId()));
            if (!Objects.equals(clockRecord.getStatus(), approvalStatus.getStatus())) {
                clockRecord.setDealType("系统未通过");
                sysProblemList.add(clockRecord);
            }
        }
        // 放入缓存
        qinCache.saveCache(CacheKey.ProblemRecord,sysProblemList);
    }
}
package com.newegg.core.service.service.impl;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.domain.PageHelper;
import com.newegg.core.service.cache.Cache;
import com.newegg.core.service.domain.ApprovalStatus;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.Page;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecords;
import com.newegg.core.service.repository.impl.ClockRecordsRepo;
import com.newegg.core.service.service.ClockRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ClockRecordsServiceImpl implements ClockRecordsService {
    @Autowired
    private ClockRecords clockRecordsRepo;

    @Autowired
    private Cache cache;

    @Override
    public void approval(List<ApprovalStatus> approvalStatus) {
        clockRecordsRepo.updateClockRecords(approvalStatus);
        check(approvalStatus);
    }

    @Override
    public void pretreatmentApproval(Query query, ArrayList<ClockRecord> list) {
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
<<<<<<< HEAD:core-service1.0.0/src/main/java/com/newegg/core/service/service/impl/ClockRecordsServiceImpl.java
    public List<ClockRecord> getRecords(Query query) {
        List<ClockRecord> clockRecordList = clockRecordsRepo.getClockRecordsList(query);
        return clockRecordList;
=======
    public List<ClockRecord> getRecords(Query query, Page page) {
        ArrayList<ClockRecord> clockRecordList = null;
        if(query.getPage()==1){
            clockRecordList=(ArrayList<ClockRecord>) clockRecordsRepo.getClockRecordsList(query);
            pretreatmentApproval(query,clockRecordList);
            cache.deleteCache(CacheKey.ClockRecord);
            cache.saveCache(CacheKey.ClockRecord,clockRecordList);
        }else{
            clockRecordList= cache.getCache(CacheKey.ClockRecord);
        }
        List<ClockRecord> returnList= PageHelper.page(clockRecordList,query.getLimit(),query.getPage());

        filter(returnList,query.getApprovalStatus());

        int size=clockRecordList.size();
        int pageTotal=size/query.getLimit();
        page.setPageNow(query.getPage());
        page.setDataTotal(size);
        page.setPageTotal(size%query.getLimit()==0?pageTotal:pageTotal+1);
        return returnList;
    }

    private void filter(List<ClockRecord> returnList, String approvalStatus) {
        returnList.forEach(e->{
            if(!Objects.equals(e.getStatus(),approvalStatus))
                returnList.remove(e);
        });
>>>>>>> a42263d315a06cc0870010fa7c08f83323cc5b63:core-service2.0.0/src/main/java/com/newegg/core/service/service/impl/ClockRecordsServiceImpl.java
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
        cache.saveCache(CacheKey.ProblemRecord,sysProblemList);
    }
}
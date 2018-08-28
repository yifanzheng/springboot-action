package com.newegg.core.service.service.impl;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.domain.PageHelper;
import com.newegg.core.service.cache.Cache;
import com.newegg.core.service.domain.*;
import com.newegg.core.service.repository.GeneralRecords;
import com.newegg.core.service.service.GeneralRecordsService;
import com.newegg.core.service.service.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GeneralRecordsServiceImpl implements GeneralRecordsService {

    @Autowired
    private GeneralRecords generalRecordsRepo;
    @Autowired
    private Leave chain;
    @Autowired
    private Cache cache;


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
    public List<GeneralRecord> getRecords(Query query, Page page) {
        ArrayList<GeneralRecord> generalRecordList = null;
        if(query.getPage()==1){
            generalRecordList=(ArrayList<GeneralRecord>) generalRecordsRepo.getGeneralRecordsList(query);
            generalRecordList.forEach(e->chain.process(e));
            cache.deleteCache(CacheKey.GeneralRecord);
            cache.saveCache(CacheKey.GeneralRecord,generalRecordList);
        }else{
            generalRecordList=cache.getCache(CacheKey.GeneralRecord);
        }
        List<GeneralRecord> returnList= PageHelper.page(generalRecordList,query.getLimit(),query.getPage());

        filter(returnList,query.getApprovalStatus());

        int size=generalRecordList.size();
        int pageTotal=size/query.getLimit();
        page.setPageNow(query.getPage());
        page.setDataTotal(size);
        page.setPageTotal(size%query.getLimit()==0?pageTotal:pageTotal+1);
        return returnList;
    }

    private void filter(List<GeneralRecord> returnList, String approvalStatus) {
        for(int i=0;i<returnList.size();i++){
            GeneralRecord e = returnList.get(i);
            if(!Objects.equals(e.getStatus(),approvalStatus))
                returnList.remove(e);

        }
        /*returnList.forEach(e->{

        });*/
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
        cache.saveCache(CacheKey.ProblemRecord,sysProblemList);
    }
}

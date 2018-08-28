package com.newegg.core.service.service.impl;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.domain.PageHelper;
import com.newegg.core.service.cache.Cache;
import com.newegg.core.service.domain.*;
import com.newegg.core.service.repository.ClockRecords;
import com.newegg.core.service.repository.GeneralRecords;
import com.newegg.core.service.repository.impl.ClockRecordsRepo;
import com.newegg.core.service.repository.impl.GeneralRecordsRepo;
import com.newegg.core.service.service.ClockRecordsService;
import com.newegg.core.service.service.ProblemRecordService;
import com.newegg.core.service.service.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProblemRecordServiceImpl implements ProblemRecordService {

    @Autowired
    private ClockRecords clockRecordsRepo;
    @Autowired
    private GeneralRecords generalRecordsRepo;
    @Autowired
    private ClockRecordsService clockRecordsService;
    @Autowired
    private Leave chain;
    @Autowired
    private Cache cache;
    @Override
    public List<ProblemRecord> getRecord(Query query, Page page) {
        ArrayList<ProblemRecord> problemRecords=null;
        //如果是访问第一页，那就需要重新预处理，并且更新缓存 否则的话直接从缓存拿
        if(query.getPage()==1){
            //得到全部，并且预处理
            ArrayList<ClockRecord> clockRecords= (ArrayList<ClockRecord>) clockRecordsRepo.getClockRecordsList(query);
            ArrayList<GeneralRecord> generalRecords= (ArrayList<GeneralRecord>) generalRecordsRepo.getGeneralRecordsList(query);
            clockRecordsService.pretreatmentApproval(query,clockRecords);
            generalRecords.forEach(e->chain.process(e));
            problemRecords=filterProblemRecords(clockRecords,generalRecords);
            //先清空缓存为了完全同步，然后再存到缓存,分页读取需要
            cache.deleteCache(CacheKey.ProblemRecord);
            cache.saveCache(CacheKey.ProblemRecord,problemRecords);
        }else{
            problemRecords=cache.getCache(CacheKey.ProblemRecord);
        }
        //分页
        List<ProblemRecord> returnList= PageHelper.page(problemRecords,query.getLimit(),query.getPage());

        filter(returnList,query.getApprovalStatus());

        int size=problemRecords.size();
        int pageTotal=size/query.getLimit();
        page.setPageNow(query.getPage());
        page.setDataTotal(size);
        page.setPageTotal(size%query.getLimit()==0?pageTotal:pageTotal+1);
        return returnList;
    }

    private void filter(List<ProblemRecord> returnList, String approvalStatus) {
        returnList.forEach(e->{
            if(!Objects.equals(e.getStatus(),approvalStatus))
                returnList.remove(e);
        });
    }

    //保留‘预处理不合格’和‘系统未通过’的单据
    private ArrayList<ProblemRecord> filterProblemRecords(ArrayList<ClockRecord> clockRecords, ArrayList<GeneralRecord> generalRecords) {
        clockRecords.forEach(e->{
            if(!Objects.equals(e.getDealType(),"预处理不合格")&&
                    !Objects.equals(e.getDealType(),"系统未通过"))
                clockRecords.remove(e);
        });
        generalRecords.forEach(e->{
            if(!Objects.equals(e.getDealType(),"预处理不合格")&&
                    !Objects.equals(e.getDealType(),"系统未通过"))
                generalRecords.remove(e);
        });
        return ProblemRecord.merge(clockRecords,generalRecords);
    }
}

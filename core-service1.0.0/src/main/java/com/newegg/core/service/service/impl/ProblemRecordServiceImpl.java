package com.newegg.core.service.service.impl;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.cache.PageHelper;
import com.newegg.core.service.cache.QinCache;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.domain.ProblemRecord;
import com.newegg.core.service.domain.Query;
import com.newegg.core.service.repository.ClockRecordsRepo;
import com.newegg.core.service.repository.GeneralRecordsRepo;
import com.newegg.core.service.service.ClockRecordsService;
import com.newegg.core.service.service.ProblemRecordService;
import com.newegg.core.service.service.qin.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProblemRecordServiceImpl implements ProblemRecordService {

    @Autowired
    private ClockRecordsRepo clockRecordsRepo;
    @Autowired
    private GeneralRecordsRepo generalRecordsRepo;
    @Autowired
    private ClockRecordsService clockRecordsService;
    @Autowired
    private Leave chain;
    @Autowired
    private QinCache qinCache;
    @Override
    public ArrayList<ProblemRecord> getRecord(Query query) {
        //得到全部，并且预处理
        ArrayList<ClockRecord> clockRecords= (ArrayList<ClockRecord>) clockRecordsRepo.getClockRecordsList(query);
        ArrayList<GeneralRecord> generalRecords= (ArrayList<GeneralRecord>) generalRecordsRepo.getGeneralRecordsList(query);
        clockRecordsService.pretreatmentApproval(query,clockRecords);
        generalRecords.forEach(e->chain.process(e));

        //只保留‘预处理不合格’和‘系统未通过’的单据
        clockRecords.forEach(e->{
            if(!Objects.equals(e.getDealType(),"预处理不合格")&&
            !Objects.equals(e.getDealType(),"系统未通过"))
                clockRecords.remove(e);
        });

        //合并两种单据到问题单据
        ArrayList<ProblemRecord> problemRecords=ProblemRecord.merge(clockRecords,generalRecords);

        //存到缓存,分页要读取的
        qinCache.saveCache(CacheKey.ProblemRecord,problemRecords);

        //分页
        ArrayList<ProblemRecord> returnList=PageHelper.page(problemRecords,query.getLimit(),query.getPage());
        return returnList;
    }
}

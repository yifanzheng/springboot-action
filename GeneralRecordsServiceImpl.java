package com.newegg.core.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.newegg.core.service.cache.Cache;
import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.domain.*;
import com.newegg.core.service.repository.IRecordsRepo;
import com.newegg.core.service.service.GeneralRecordsService;
import com.newegg.core.service.service.Leave;
import com.newegg.core.service.util.CachePair;
import com.newegg.core.service.util.PageHelper;
import com.newegg.core.service.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GeneralRecordsServiceImpl implements GeneralRecordsService {

    @Autowired
    private IRecordsRepo<GeneralRecord> generalRecordsRepo;
    @Autowired
    private Leave chain;
    @Autowired
    private Cache cache;
    private ThreadUtil threadUtil = new ThreadUtil();
    private CachePair cachePair = new CachePair();

    @Override

    public void approval(List<ApprovalStatus> approvalStatus) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String updateRecordsResult = generalRecordsRepo.updateRecords(approvalStatus);
                check(updateRecordsResult, approvalStatus);
            }
        }).start();
    }

    @Override
    public GeneralRecord getRecord(String id) {
        return generalRecordsRepo.getRecord(id);
    }

    /**
     * 获取单据列表，并交给chain处理
     *
     * @param query
     * @return 处理后的单据
     */
    @Override
    public List<GeneralRecord> getRecords(Query query, Page page) {
        ArrayList<GeneralRecord> generalRecordList = getRecords(query);
        filter(generalRecordList, query);
        List<GeneralRecord> returnList = PageHelper.page(generalRecordList, query.getLimit(), query.getPage());
        setPage(page, generalRecordList, query);
        return returnList;
    }

    @Override
    public void check(String resultList, List<ApprovalStatus> approvalStatus) {
        List<ApprovalStatus> approvalStatusList = JSON.parseArray(resultList, ApprovalStatus.class);
        ArrayList<GeneralRecord> sysProblemList = new ArrayList<>();
        for (ApprovalStatus approvalNew : approvalStatusList) {
            for (ApprovalStatus approvalOld : approvalStatus) {
                if (approvalNew.getId().equals(approvalOld.getId()) && approvalNew.getStatus() != approvalOld.getStatus()) {
                    GeneralRecord generalRecord = (GeneralRecord) cache.getCache(CacheKey.GeneralRecord, approvalNew.getId());
                    generalRecord.setDealType(7);
                    sysProblemList.add(generalRecord);
                }
            }
        }
        cache.deleteCache(CacheKey.SysErrorGeneralRecord);
        cache.saveCache(CacheKey.SysErrorGeneralRecord, sysProblemList);
    }

    private void setPage(Page page, ArrayList<GeneralRecord> generalRecordList, Query query) {
        int size = generalRecordList.size();
        int pageTotal = size / query.getLimit();
        page.setPageNow(query.getPage());
        page.setDataTotal(size);
        page.setPageTotal(size % query.getLimit() == 0 ? pageTotal : pageTotal + 1);
    }

    private ArrayList<GeneralRecord> getRecords(Query query) {
        ArrayList<GeneralRecord> returnList = null;
        System.out.println("hhh");
        if (query.getPage() == 1 || !cache.IsAlreadyCache(CacheKey.GeneralRecord)) {
            returnList = (ArrayList<GeneralRecord>) generalRecordsRepo.getRecordsList(query);
            returnList.forEach(e -> chain.process(e));
            cache.deleteCache(CacheKey.GeneralRecord);
            cache.saveCache(CacheKey.GeneralRecord, returnList);
        } else {
            returnList = cache.getCache(CacheKey.GeneralRecord);
        }
        return returnList;
    }

    private void filter(List<GeneralRecord> returnList, Query query) {
        Integer approvalStatus = query.getApprovalStatus();
        if (approvalStatus == null || approvalStatus == 0) {
            setDailyWorkTime(returnList);
            filterHours(returnList, query);
            return;
        }
        Iterator it = returnList.iterator();
        while (it.hasNext()) {
            GeneralRecord generalRecord = (GeneralRecord) it.next();
            if (approvalStatus <= 6) {
                if (!Objects.equals(generalRecord.getStatus(), approvalStatus))
                    it.remove();
            } else {
                if (!Objects.equals(generalRecord.getDealType(), approvalStatus))
                    it.remove();
            }
        }
        setDailyWorkTime(returnList);
        filterHours(returnList, query);
    }

    //计算每天的工作时间
    private void setDailyWorkTime(List<GeneralRecord> returnList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Iterator it = returnList.iterator();
        while (it.hasNext()) {
            GeneralRecord generalRecord = (GeneralRecord) it.next();
            List<Integer> dailyWorkTime = new ArrayList<>();
            Iterator it2 = generalRecord.getPunchRecordList().iterator();
            while (it2.hasNext()) {
                List<String> list = (List<String>) it2.next();
                if (list.size() != 2)
                    continue;
                try {
                    String ymd = list.get(0).split(" ")[0];
                    long firstPunchTime = sdf.parse(list.get(0)).getTime();//第一次打卡时间

                    long endPunchTime = sdf.parse(list.get(1)).getTime();//最后一次打卡时间
                    //实际工作时间
                    int diffHour = 0;
                    if (generalRecord.getShift().contains("弹")) {

                        long startWorkTime = sdf.parse(ymd + " 7:30:00").getTime();
                        long endWorkTime = sdf.parse(ymd + " 18:30:00").getTime();
                        diffHour =getDailyWorkTime(firstPunchTime,endPunchTime,startWorkTime,endWorkTime,ymd);
                    }else {
                        long startWorkTime = sdf.parse(ymd + " 8:30:00").getTime();
                        long endWorkTime = sdf.parse(ymd + " 1730:00").getTime();
                        diffHour =getDailyWorkTime(firstPunchTime,endPunchTime,startWorkTime,endWorkTime,ymd);
                    }
                    dailyWorkTime.add(diffHour);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            generalRecord.setDailyWorkTime(dailyWorkTime);
        }
    }

    //更新的方法
    public int getDailyWorkTimeNew(long firstPunchTime,long endPunchTime, long startWorkTime,long endWorkTime,String ymd){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //12点到13点
        long morning = 0;
        long afternoon = 0;
        try {
            //12点到13点时间
            morning = sdf.parse(ymd + " 12:00:00").getTime();
            afternoon = sdf.parse(ymd + " 13:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long startOffSet=morning-firstPunchTime;
        long endOffSet=endPunchTime-afternoon;
        //休息时间（12:00-13:00）一小时
        long restTimes=afternoon-morning;
        long start = getTime(startOffSet, morning - startWorkTime, morning - endWorkTime, restTimes);
        long end=getTime(endOffSet,endWorkTime-afternoon,startWorkTime-morning,restTimes);
        return getDiffHour(start+end);

    }

    //单个时间段返回的值
    public long getTime(long offSet,long max_limit,long min_limit,long restTimes){
        if(offSet>0){
            if(offSet>max_limit){
                offSet=max_limit;
            }
        }else {
            if(offSet>restTimes){
                offSet=0;
            }else {
                if(offSet<min_limit){
                    offSet=min_limit+restTimes;
                }else {
                    offSet+=restTimes;
                }
            }
        }
       return offSet;
    }

    //TODO 获取实际工作时间
    public int getDailyWorkTime(long firstPunchTime,long endPunchTime, long startWorkTime,long endWorkTime,String ymd){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换时间戳比较
        Long diff = 0l;
        //实际工作时间
        int diffHour = 0;
        //12点到13点
        long morning = 0;
        long afternoon = 0;
        try {
            //12点到13点时间
            morning = sdf.parse(ymd + " 12:00:00").getTime();
            afternoon = sdf.parse(ymd + " 13:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //第一次打卡在7.30后，最后一次打卡在18.30前
        if (firstPunchTime >= startWorkTime && endPunchTime < endWorkTime) {
            //第一次打卡在12点前，最后一次打卡在13点后
            if (firstPunchTime < morning && endPunchTime > afternoon) {
                diff = endPunchTime - firstPunchTime;
                diffHour -= 1;
            }
            //第一次打卡在12点前，最后一次打卡在13点前
            else if (firstPunchTime < morning && endPunchTime < afternoon) {
                diff = morning - firstPunchTime;
            }
            //第一次打卡在13点后，最后一次打卡在18点前
            else if (firstPunchTime > afternoon && endPunchTime < endWorkTime) {
                diff = endPunchTime - firstPunchTime;
            }
            //第一次打卡在12.0到13.0之间，最后一次打卡在18.30之前
            else if (firstPunchTime > morning && firstPunchTime < afternoon && endPunchTime < endWorkTime) {
                diff = endPunchTime - afternoon;
            }
        }
        //第一次打卡在7.30后，最后一次打卡在18.30后
        else if (firstPunchTime > startWorkTime && endPunchTime > endWorkTime) {
            //第一次打卡在12点前
            if (firstPunchTime < morning) {
                diff = endWorkTime - firstPunchTime;
                diffHour -= 1;
            }
            //第一次打卡在13点后
            else if (firstPunchTime > afternoon) {
                diff = endPunchTime - firstPunchTime;
            }
            //第一次打卡在12.0到13.0之间
            else if (firstPunchTime > morning && firstPunchTime < afternoon) {
                diff = endWorkTime - afternoon;
            }
        }
        //第一次打卡在7.30以前，最后一次在18.30以前
        else if (firstPunchTime < startWorkTime && endPunchTime < endWorkTime) {
            //第一次打卡在7.30以前，最后一次打卡在18.30以前
            if (endPunchTime > afternoon) {
                diff = endPunchTime - startWorkTime;
                diffHour -= 1;
            }
            //第一次打卡在7.30以前，最后一次打卡在12以前
            else if (endPunchTime < morning) {
                diff = endPunchTime - startWorkTime;
            }
            //第一次打卡在7.30以前，最后一次打卡在12在13之间
            else if (endPunchTime > morning && endPunchTime < afternoon) {
                diff = morning - firstPunchTime;
            }
        }
        diffHour += getDiffHour(diff);//计算时间戳相差时数
        return diffHour;
    }

    //获取实际打卡时间
    public int getDiffHour(long diff) {
        return (int) (diff / (1000 * 60 * 60));
    }

    //筛选出相应请假时长的单据
    private void filterHours(List<GeneralRecord> returnList, Query query) {
        Integer hourIndex = query.getHourIndex();
        if (hourIndex == null || hourIndex == 0)
            return;
        switch (hourIndex) {
            case 1:
                Iterator it = returnList.iterator();
                while (it.hasNext()) {
                    GeneralRecord record = (GeneralRecord) it.next();
                    if (record.getSumtime() > 8)
                        it.remove();

                }
                break;
            case 2:
                Iterator it2 = returnList.iterator();
                while (it2.hasNext()) {
                    GeneralRecord record = (GeneralRecord) it2.next();
                    if (record.getSumtime() <= 8)
                        it2.remove();

                }
                break;
            case 3:
                Iterator it3 = returnList.iterator();
                while (it3.hasNext()) {
                    GeneralRecord record = (GeneralRecord) it3.next();
                    if (record.getSumtime() > 16)
                        it3.remove();

                }
                break;
            case 4:
                Iterator it4 = returnList.iterator();
                while (it4.hasNext()) {
                    GeneralRecord record = (GeneralRecord) it4.next();
                    if (record.getSumtime() <= 16)
                        it4.remove();

                }
                break;
        }
    }

}

package com.newegg.core.service.cache;

import com.newegg.core.service.domain.*;
import com.newegg.core.service.service.ClockRecordsService;
import com.newegg.core.service.service.qin.Leave;
import com.newegg.core.service.service.qin.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * by:qin
 */
@Component
public class QinCache {
    @Autowired
    @Qualifier("entry")
    private Leave leaveChain;
    @Autowired
    private ClockRecordsService clockService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获得最新的缓存
     * @return
     * @param target 哪一个单据的缓存
     * @param query 哪种条件下的缓存
     * @param list 最新的restful数据
     */
    public <T extends Record> ArrayList<T> getNewestRecord(CacheKey target, Query query, ArrayList<T> list) {
        ArrayList<T> oldCache=getCache(target,query);//老的缓存数据
        ArrayList<Integer> addCache=new ArrayList<>();//需要额外添加的缓存
        ArrayList<Integer> removeCache=new ArrayList<>();//需要删除的缓存

        ArrayList<Integer> restfulIds=new ArrayList<>();
        ArrayList<Integer> restfulIdsCopy=new ArrayList<>();
        ArrayList<Integer> oldCacheIds=new ArrayList<>();

        list.forEach(e->{
            Integer id= Integer.valueOf(e.getId());
            restfulIds.add(id);
            restfulIdsCopy.add(id);
        });
        oldCache.forEach(e->oldCacheIds.add(Integer.valueOf(e.getId())));

        //向缓存中添加缺少的数据
        restfulIds.removeAll(oldCacheIds);
        if(restfulIds.size()!=0){
            restfulIds.forEach(e->addCache.add(e));
            appendCache(target,list,addCache,query);
        }
        //删除缓存中的过期数据
        oldCacheIds.removeAll(restfulIdsCopy);
        if (oldCacheIds.size()!=0){
            oldCacheIds.forEach(e->removeCache.add(e));
            removeCache(target,list,removeCache,query);
        }

        //restful中没有数据,则直接清空缓存
        if(list==null||list.size()==0){
            clearCache(target,query);
            return null;
        }
        return null;
    }

    /**
     *
     * @param <T>
     * @param target 哪一个单据的缓存，
     * @param list 最新的restful数据
     * @param removeCache 需要从缓存中移除的，数据的id
     * @param query
     */
    private <T extends Record> void removeCache(CacheKey target, ArrayList<T> list, ArrayList<Integer> removeCache, Query query) {
        if(target==CacheKey.ClockRecord){
            ArrayList<ClockRecord> clockRecords=new ArrayList<>();
            list.forEach(e->{
                removeCache.forEach(id->{
                    if(e.getId().equals(id))
                        clockRecords.add((ClockRecord) e);
                });
            });
            //接着调用API删除缓存里的clockRecords
            //TODO
        }else if(target==CacheKey.GeneralRecord){
            ArrayList<GeneralRecord> generalRecords=new ArrayList<>();
            list.forEach(e->{
                removeCache.forEach(id->{
                    if(e.getId().equals(id))
                        generalRecords.add((GeneralRecord) e);
                });
            });
            //接着调用AP删除缓存里的generalRecords
            //TODO
        }
    }

    /**
     *
     * @param <T>
     * @param target 哪一个单据的缓存
     * @param list 最新的restful数据
     * @param addCache 需要追加进缓存里的，数据的id
     * @param query
     */
    private <T extends Record> void appendCache(CacheKey target, ArrayList<T> list, ArrayList<Integer> addCache, Query query) {
        if(target==CacheKey.ClockRecord){
            Result result=new Result();
            ArrayList<ClockRecord> clockRecords=new ArrayList<>();
            list.forEach(e->{
                addCache.forEach(id->{
                    if(e.getId().equals(id))
                        clockRecords.add((ClockRecord) e);
                });
            });
            clockService.pretreatmentApproval(query,clockRecords);
            saveCache(target,clockRecords);
        }else if(target==CacheKey.GeneralRecord){
            Result result=new Result();
            ArrayList<GeneralRecord> generalRecords=new ArrayList<>();
            list.forEach(e->{
                addCache.forEach(id->{
                    if(e.getId().equals(id))
                        generalRecords.add((GeneralRecord) e);
                });
            });
            generalRecords.forEach(e->{
                leaveChain.process(e);
            });
            saveCache(target,generalRecords);
        }
    }

    public void saveCache(CacheKey target, ArrayList records) {
        if(target==CacheKey.ClockRecord){

            redisUtil.set(CacheKey.ClockRecord.toString(),"1",records);
        }else if(target==CacheKey.GeneralRecord){

            redisUtil.set(CacheKey.GeneralRecord.toString(),"1",records);
        }
    }

    private void clearCache(CacheKey target, Query query) {

        //TODO
    }


    /**
     * 获得缓存，但不保证是最新的
     * @param <T>
     * @param target 哪一个单据的缓存
     * @param query 哪种条件下的缓存
     * @return
     */
    private <T> ArrayList<T> getCache(CacheKey target, Query query) {
        if(target==CacheKey.GeneralRecord){
            ArrayList<GeneralRecord> generalRecords=redisUtil.get(target.toString());
            ArrayList<GeneralRecord> returnList=new ArrayList<>();
            //TODO 修改
            generalRecords.forEach(e->{
                if(query.getRecordType().equals(e.getDocumentType())&&
                        (query.getStartTime().compareTo(e.getStartTime())==0)&&
                        (query.getEndTime().compareTo(e.getEndTime())==0)&&
                        query.getApprovalStatus().equals(e.getDealType())
                ){
                    returnList.add(e);
                }
            });
        }else if(target==CacheKey.ClockRecord){
            ArrayList<ClockRecord> clockRecords=redisUtil.get(target.toString());
            ArrayList<ClockRecord> returnList=new ArrayList<>();
            clockRecords.forEach(e->{

            });
        }else if(target==CacheKey.ProblemRecord){

        }else if(target==CacheKey.Approved){

        }else if(target==CacheKey.PretreatmentFail){

        }else if(target==CacheKey.PretreatmentOk){

        }else if(target==CacheKey.SystemFail){

        }
        return null;
    }

    public boolean IsAlreadyCache(CacheKey target) {
        return redisUtil.hasKey(target.toString());
    }

    /**
     * 获得用户以前点击的查询条件
     * @return
     */
    public Query getQueryCriteria() {
        Query query= (Query) redisUtil.get(CacheKey.QueryCriteria+"",1+"");//hk值用1
        return query;
    }

    /**
     * 缓存查询条件
     * @param query
     */
    public void saveQueryCriteria(Query query) {
        redisUtil.set(CacheKey.QueryCriteria+"",1+"",query);
    }

    /**
     * 获得问题单据（预处理未通过、系统未通过）
     * @return
     * @param generalList restful最新的一般单据
     * @param clockList restful最新的签卡单据
     */
    public ArrayList<ProblemRecord> getProblemRecords(ArrayList<GeneralRecord> generalList, ArrayList<ClockRecord> clockList) {
        //TODO
        return null;
    }
}

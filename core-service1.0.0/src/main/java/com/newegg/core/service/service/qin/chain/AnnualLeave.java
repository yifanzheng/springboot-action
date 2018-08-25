package com.newegg.core.service.service.qin.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.qin.Leave;

import java.util.Objects;

/**
 * 年假
 */
public class AnnualLeave extends Leave {

    @Override
    public void process(GeneralRecord record) {
        if(Objects.equals("年假",record.getDocumentType())){
            //判断申请年假是否通过
            if(record.getHours()>record.getSumtime()){
                record.setDealType("预处理不合格");
                record.setProblemReason("请假时间超出规定时间");
            }else if(Objects.equals(record.getAttachment(),null)||"".equals(record.getAttachment())){
                record.setDealType("预处理不合格");
                record.setProblemReason("没有附件");
            }else{
                record.setDealType("预处理合格");
            }

        } else if(getSuccessor()!=null){
                getSuccessor().process(record);
        }
    }
}

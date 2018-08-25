package com.newegg.core.service.service.qin.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.qin.Leave;

import java.util.Objects;

/**
 * 婚假
 */
public class MarriageLeave extends Leave {


    @Override
    public void process(GeneralRecord record) {

        if(Objects.equals("婚假",record.getDocumentType())){
            //判断申请婚假是否通过
            if(record.getHours()<=24){
                record.setDealType("预处理合格");
            }else {
                record.setDealType("预处理不合格");
                record.setProblemReason("请假时间超出规定范围");
            }

        } else if(getSuccessor()!=null){
            getSuccessor().process(record);
        }
    }
}

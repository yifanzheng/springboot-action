package com.newegg.core.service.service.qin.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.qin.Leave;

import java.util.Objects;

public class NursingLeave extends Leave {
    @Override
    public void process(GeneralRecord record) {

        if(Objects.equals("护理假",record.getDocumentType())){
            //判断申请护理假是否通过
            if(record.getHours()>160){
                record.setDealType("预处理不合格");
                record.setProblemReason("请假时间超出规定范围");
            }else if(Objects.equals(record.getAttachment(),null)||"".equals(record.getAttachment())){
                record.setDealType("预处理不合格");
                record.setProblemReason("没有附件");
            }
        } else if(getSuccessor()!=null){
            getSuccessor().process(record);
        }
    }
}

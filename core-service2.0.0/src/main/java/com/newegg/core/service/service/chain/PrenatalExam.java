package com.newegg.core.service.service.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.Leave;

import java.util.Objects;

/**
 * @Auther: sz7v
 * @Date: 2018/8/23 17:28
 * @Description:
 */
public class PrenatalExam extends Leave {
    @Override
    public void process(GeneralRecord record) {
        if(Objects.equals("产前检查",record.getDocumentType())){
            if(Objects.equals(record.getAttachment(),null)||Objects.equals("",record.getAttachment())){
                record.setDealType("预处理不合格");
                record.setProblemReason("没有附件");
            }else {
                record.setDealType("预处理合格");
            }
        } else if(getSuccessor()!=null){
            getSuccessor().process(record);

        }
    }
}

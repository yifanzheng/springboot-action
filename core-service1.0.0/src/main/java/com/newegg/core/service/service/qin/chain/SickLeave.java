package com.newegg.core.service.service.qin.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.qin.Leave;

import java.util.Objects;

/**
 * 病假
 */
public class SickLeave extends Leave {
    @Override
    public void process(GeneralRecord record) {

          if(Objects.equals("病假",record.getDocumentType())){
                //判断申请年假是否通过
                if(record.getHours()<=8){
                    record.setDealType("预处理合格");
                }else if(record.getHours()>8
                        ||Objects.equals(record.getAttachment(),null)
                        ||Objects.equals("",record.getAttachment())){
                   record.setProblemReason("没有附件");
                   record.setDealType("预处理不合格");
                }

            } else if(getSuccessor()!=null){
              getSuccessor().process(record);
          }
    }
}

package com.newegg.core.service.service.qin.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.qin.Leave;

import java.util.Objects;

/**
 * Harry
 * 产假判断有无附件
 **/
public class GestationLeave extends Leave {
    @Override
    public void process(GeneralRecord record) {
        if (Objects.equals("产假", record.getDocumentType())) {
            if (Objects.equals(record.getAttachment(), null) || "".equals(record.getAttachment())) {
                record.setProblemReason("没有附件");
                record.setDealType("预处理不合格");
            }

        } else if (getSuccessor() != null) {
            getSuccessor().process(record);
        }
    }
}

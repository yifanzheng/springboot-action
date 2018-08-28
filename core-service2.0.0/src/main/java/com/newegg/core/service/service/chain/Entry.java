package com.newegg.core.service.service.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.Leave;

import java.util.Objects;

/**
 * @Auther: sz7v
 * @Date: 2018/8/23 16:30
 * @Description:
 */
public class Entry extends Leave {
    @Override
    public void process(GeneralRecord record) {
        if(Objects.equals(record.getStatus(),"待审批")){
            getSuccessor().process(record);
        }
    }
}

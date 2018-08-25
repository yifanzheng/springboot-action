package com.newegg.core.service.service.qin.chain;

import com.newegg.core.service.domain.GeneralRecord;
import com.newegg.core.service.service.qin.Leave;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Auther: sz7v
 * @Date: 2018/8/23 16:30
 * @Description:
 */
public class Entry extends Leave {
    @Override
    public void process(GeneralRecord record) {
        if(Objects.equals(record.getStatus(),"未审批")){
            getSuccessor().process(record);
        }
    }
}

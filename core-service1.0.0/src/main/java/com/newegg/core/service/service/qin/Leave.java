package com.newegg.core.service.service.qin;

import com.newegg.core.service.domain.GeneralRecord;

/**
 * by:qin
 * 各种请假的抽象类
 */
public abstract class Leave {
    private Leave successor;

    public Leave getSuccessor() {
        return successor;
    }

    public void setSuccessor(Leave successor) {
        this.successor = successor;
    }

    public abstract void process(GeneralRecord record);
}

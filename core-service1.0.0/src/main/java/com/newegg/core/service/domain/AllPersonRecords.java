package com.newegg.core.service.domain;

import java.util.List;

/**
 *  Harry
 *  所有员工的records，包括一般单据model和签卡单据model
 * **/
public class AllPersonRecords {
    private List<GeneralRecord> generalRecords; // 所有人的一般单据
    private List<ClockRecord> clockRecords; // 所有人的签卡单据

    public AllPersonRecords() {
    }

    public AllPersonRecords(List<GeneralRecord> generalRecords, List<ClockRecord> clockRecords) {
        this.generalRecords = generalRecords;
        this.clockRecords = clockRecords;
    }

    public List<GeneralRecord> getGeneralRecords() {
        return generalRecords;
    }

    public void setGeneralRecords(List<GeneralRecord> generalRecords) {
        this.generalRecords = generalRecords;
    }

    public List<ClockRecord> getClockRecords() {
        return clockRecords;
    }

    public void setClockRecords(List<ClockRecord> clockRecords) {
        this.clockRecords = clockRecords;
    }
}

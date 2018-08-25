package com.newegg.core.service.cache;

/**
 * 在redis缓存中的key
 */
public enum CacheKey {
    ClockRecord,
    GeneralRecord,
    ProblemRecord,//‘问题单据’缓存是‘预处理未通过’和‘系统未通过’缓存的超集
    PretreatmentOk,
    PretreatmentFail,
    SystemFail,
    Approved,
    QqueryCriteria
}

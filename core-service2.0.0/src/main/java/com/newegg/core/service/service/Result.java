package com.newegg.core.service.service;

/**
 * 用来存放各种请假的处理结果
 */
public class Result {
    //private CopyOnWriteArrayList<>

    private Object 年假处理结果;

    private Object 病假处理结果;

    private Object 婚假处理结果;

    public Object get年假处理结果() {
        return 年假处理结果;
    }

    public void set年假处理结果(Object 年假处理结果) {
        this.年假处理结果 = 年假处理结果;
    }

    public Object get病假处理结果() {
        return 病假处理结果;
    }

    public void set病假处理结果(Object 病假处理结果) {
        this.病假处理结果 = 病假处理结果;
    }

    public Object get婚假处理结果() {
        return 婚假处理结果;
    }

    public void set婚假处理结果(Object 婚假处理结果) {
        this.婚假处理结果 = 婚假处理结果;
    }
}

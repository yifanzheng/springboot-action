package com.example.springboot.timer.util;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Star.Y.Zheng
 */
public final class ThreadPoolUtil {


    private static class SingelThreadPool {

        private final ScheduledExecutorService SCHEDULE_SERVICE;

        SingelThreadPool() {

            SCHEDULE_SERVICE = Executors.newScheduledThreadPool(3);

        }

        private static SingelThreadPool getInstance() {

            return new SingelThreadPool();
        }
    }


    public static ScheduledExecutorService getScheduledExecutorService() {

        return SingelThreadPool.getInstance().SCHEDULE_SERVICE;
    }


}

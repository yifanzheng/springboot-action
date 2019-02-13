package com.example.springboot.timer.util;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Star.Y.Zheng
 */
public final class ThreadPoolUtil {


    private static class SingleThreadPool {

        private final ScheduledExecutorService scheduleExecutor;

        SingleThreadPool() {

            scheduleExecutor = Executors.newScheduledThreadPool(3);

        }

        private ScheduledExecutorService getSchedule() {

            return SCHEDULE_SERVICE;
        }
    }

    private static class SingleThreadPoolHolder {
        private static final SingleThreadPool INSTANCE = new SingleThreadPool();
    }


    public static ScheduledExecutorService getScheduledExecutorService() {

        return SingelThreadPoolHolder.INSTANCE;
    }


}

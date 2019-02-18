package com.example.springboot.timer.service;

/**
 * @author Star.Y.Zheng
 */

import com.example.springboot.timer.task.TaskRunnable;
import com.example.springboot.timer.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private static final ConcurrentHashMap<Integer, Future> TASK_MAP = new ConcurrentHashMap<>();

    public void startTask(Integer caseNumber) {
        // 定时线程
        ScheduledExecutorService executorService = ThreadPoolUtil.getInstance();

        // 5s后启动任务，并在上一次任务完成后推迟5s开始下一次任务
        Future<?> scheduledFuture = executorService
                  .scheduleWithFixedDelay(new TaskRunnable(), 5, 5, TimeUnit.SECONDS);

        TASK_MAP.put(caseNumber, scheduledFuture);
        logger.debug("Task started!");


    }

    public void stopTask(Integer caseNumber) {
        Future future = TASK_MAP.get(caseNumber);

        // 关闭定时器
        future.cancel(true);
        logger.debug("Task stoped!");
    }
}

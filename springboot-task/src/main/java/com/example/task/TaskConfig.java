package com.example.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 *
 * @author kevin
 * @date 2018-10-22 21:51
 **/
@Component
public class TaskConfig {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * 每隔3秒执行一次
     */
    //@Scheduled(fixedRate = 3000)
    @Scheduled(cron = "4-10 * * * * ?") // 周期在4-10秒，每隔一秒执行。-
    public void reportCurrentTime() {

        System.out.println("目前的时间："+DATE_FORMAT.format(new Date()));
    }
}

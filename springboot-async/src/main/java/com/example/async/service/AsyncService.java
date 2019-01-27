package com.example.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * 异步业务
 *
 * @author kevin
 * @date 2018-10-21 22:50
 **/
@Service
@EnableAsync
public class AsyncService {

    @Async
    public String getContent(){
        System.out.println("2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3");

        return "success!";
    }
}

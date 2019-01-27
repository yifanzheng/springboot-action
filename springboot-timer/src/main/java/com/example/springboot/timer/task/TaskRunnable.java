package com.example.springboot.timer.task;

import com.example.springboot.timer.ApplicationContextUtils;
import com.example.springboot.timer.repository.TaskRepository;

/**
 * @author Star.Y.Zheng
 */
public class TaskRunnable implements Runnable {

    private TaskRepository repo;

    public TaskRunnable() {
        this.repo = ApplicationContextUtils.getBean(TaskRepository.class);
    }

    @Override
    public void run() {
        this.repo.insertData();

    }
}

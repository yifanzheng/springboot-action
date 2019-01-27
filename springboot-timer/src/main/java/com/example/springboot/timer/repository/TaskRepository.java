package com.example.springboot.timer.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Star.Y.Zheng
 */
@Repository
public class TaskRepository {

    private final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    public void insertData() {

        System.out.println("Insert data to database!");
        logger.debug("insert data successed!");
    }
}

package com.example.springboot.timer.web;

import com.example.springboot.timer.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Star.Y.Zheng
 */
@RestController
@RequestMapping("api/schedule/task")
public class TaskResource {

    @Autowired
    private TaskService service;

    /**
     * 根据caseNumber开始任务
     * @param caseNumber
     * @return
     */
    @PostMapping("/{caseNumber}/commands/start")
    public ResponseEntity<Void> startTask(@PathVariable Integer caseNumber) {

        service.startTask(caseNumber);

        return ResponseEntity.ok().build();

    }

    /**
     * 根据caseNumber停止任务
     * @param caseNumber
     * @return
     */
    @PostMapping("/{caseNumber}/commands/stop")
    public ResponseEntity<Void> stopTask(@PathVariable Integer caseNumber) {

        service.stopTask(caseNumber);
        return ResponseEntity.ok().build();
    }
}

package com.example.druid.controller;

import com.example.druid.domain.Student;
import com.example.druid.service.impl.StudentServiceImpl;
import com.example.druid.service.impl.StudentServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制器
 *
 * @author kevin
 **/
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentServiceCustom serviceCustom;

    @GetMapping("/users/{id}")
    public ResponseEntity<Student> getUser(@PathVariable Integer id) {
        Student student = service.queryById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Student>> getUsers() {
        List<Student> students = service.queryStudentList();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(@RequestBody Student student) {
        try {
            service.save(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Save data successed");
    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@RequestBody Student student) {
        service.update(student);
        return ResponseEntity.ok("Update data successed");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("Delete data successed");
    }

    @GetMapping("/users/paged")
    public ResponseEntity<List<Student>> getStudentsByPaged(@RequestParam Integer pageIndex, @RequestParam(required = false) Integer pageSize){

        List<Student> students = service.queryStudentListPaged(pageIndex, pageSize);
        return ResponseEntity.ok(students);
    }

    // ==================自定义mapper=============

    @GetMapping("/users/custom/{id}")
    public ResponseEntity<Student> queryStudentById(@PathVariable Integer id){
        Student student = serviceCustom.selectStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/custom/users")
    public ResponseEntity<List<Student>> queryAll(){
        List<Student> students = serviceCustom.selectAll();
        return ResponseEntity.ok(students);
    }

}

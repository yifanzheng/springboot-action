package com.multi.jpa.controller;

import com.multi.jpa.secondarydatasource.domain.Student;
import com.multi.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController
 *
 * @author kevin
 **/
@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = service.getStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/student")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student) {
        int i = service.updateStudent(student);
        return ResponseEntity.ok(String.format("Update %s data successed!", i));
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") Integer id) {
        int i = service.deleteStudent(id);
        return ResponseEntity.ok(String.format("Delete %s data successed!", i));
    }
}

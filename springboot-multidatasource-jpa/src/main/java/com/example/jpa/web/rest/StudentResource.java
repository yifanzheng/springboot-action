package com.example.jpa.web.rest;

import com.example.jpa.service.StudentService;
import com.example.jpa.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class StudentResource {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getStudents(Pageable pageable) {
        List<StudentDTO> students = studentService.listStudent(pageable);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO student) {
        StudentDTO studentDTO = studentService.saveStudent(student);
        return ResponseEntity.ok(studentDTO);
    }

    @PutMapping("/students")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO student) {
        StudentDTO studentDTO = studentService.updateStudent(student);
        return ResponseEntity.ok(studentDTO);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}

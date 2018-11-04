package com.multi.jpa.service;

import com.multi.jpa.secondarydatasource.domain.Student;
import com.multi.jpa.secondarydatasource.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * StudentService
 *
 * @author kevin
 **/
@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> getStudents() {
        return repo.getStudents();
    }

    public int updateStudent(Student student) {
        return repo.updateStudent(student);
    }

    public int deleteStudent(int id) {
        return repo.deleteStudent(id);
    }
}

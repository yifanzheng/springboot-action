package com.example.jpa.service.mapper;

import com.example.jpa.entity.secondary.Student;
import com.example.jpa.service.dto.StudentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * StudentMapper
 *
 * @author star
 */
@Service
public class StudentMapper {

    public Student convertToStudent(StudentDTO dto) {
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        return student;
    }

    public StudentDTO convertForStudent(Student student) {
        StudentDTO dto = new StudentDTO();
        BeanUtils.copyProperties(student, dto);
        return dto;
    }
}

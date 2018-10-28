package com.example.druid.service.impl;

import com.example.druid.domain.Student;
import com.example.druid.mapper.StudentMapperCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义mapper的业务层
 *
 * @author kevin
 * @date 2018-10-28 10:16
 **/
@Service
public class StudentServiceCustom {

    @Autowired
    private StudentMapperCustom mapperCustom;

    public Student selectStudent(Integer id){
        Student student = mapperCustom.queryById(id);

        return student;
    }

    public List<Student> selectAll(){
        List<Student> students = mapperCustom.selectAll();
        return students;
    }
}

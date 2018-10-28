package com.example.druid.mapper;

import com.example.druid.domain.Student;

import java.util.List;

/**
 * 自定义mapper
 * 注意：方法名（queryById）要与StudentMappeCustom.xml文件中的id对应
 * @author kevin
 **/
public interface StudentMapperCustom {

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    Student queryById(Integer id);

    /**
     * 查询所有记录
     * @return
     */
    List<Student> selectAll();
}

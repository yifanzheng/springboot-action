package com.example.druid.service;

import com.example.druid.domain.Student;

import java.util.List;

/**
 * StudentService
 *
 * @author kevin
 **/
public interface StudentService {

    /**
     * 添加记录
     *
     */
    void save(Student student) throws Exception;

    /**
     * 更新记录
     *
     */
    void update(Student student);

    /**
     * 删除记录
     * @param id
     */
    void delete(Integer id);

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
    List<Student> queryStudentList();

    /**
     * 分页查询记录
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Student> queryStudentListPaged(Integer pageIndex, Integer pageSize);
}

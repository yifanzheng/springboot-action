package com.example.druid.service.impl;

import com.example.druid.domain.Student;
import com.example.druid.mapper.StudentMapper;
import com.example.druid.service.StudentService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 业务层实现类
 *
 * @author kevin
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(Student student) throws Exception {
        // 保存数据
        mapper.insert(student);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void update(Student student) {
        // mapper.updateByPrimaryKey(student); 使用此方法更新时，数据库中的该条记录的所有字段的值都会改变，传入的对象中没有值的属性在数据库中会置为null

        // -Selective 只在数据库中改变传入对象中有值的属性对应的字段，其他字段的值不变。
        mapper.updateByPrimaryKeySelective(student);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public Student queryById(Integer id) {
        Student student = mapper.selectByPrimaryKey(id);
        return student;
    }

    @Override
    public List<Student> queryStudentList() {
        List<Student> studentList = mapper.selectAll();
        return studentList;
    }

    @Override
    public List<Student> queryStudentListPaged(Integer pageIndex, Integer pageSize) {
        if (pageIndex == null) {
            pageIndex = 1;
        }

        // 如果没有指定每页的数量，默认每页是10条记录
        if (pageSize == null) {
            pageSize = 10;
        }

        // 开始分页
        PageHelper.startPage(pageIndex,pageSize);

        Example example = new Example(Student.class);
        // 根据中文成绩排序
        example.orderBy("chinese").desc();

        List<Student> students = mapper.selectByExample(example);

        return students;
    }
}

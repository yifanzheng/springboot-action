package com.example.mybatis.service;

import com.example.mybatis.entity.User;
import com.example.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService
 *
 * @author star
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名获取信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUser(String name) {
        User user = userMapper.findByName(name);

        return user;
    }

    /**
     * 添加用户数据
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertUser(String name, String password) {
        int insert = userMapper.save(name, password);

        return insert;
    }

    /**
     * 更新用户
     */
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    /**
     * 删除用户
     */
    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }
}

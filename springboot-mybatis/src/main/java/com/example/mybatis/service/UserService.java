package com.example.mybatis.service;

import com.example.mybatis.domain.Users;
import com.example.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务逻辑层
 *
 * @author kevin
 * @date 2018-10-22 11:15
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    /**
     * 根据用户名获取信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users getUser(String name){
        Users user = mapper.findByName(name);
        return user;

    }

    /**
     * 添加用户数据
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public int insertUser(String name,String password){
        int insert = mapper.insert(name, password);
        // int a=1/0;
        return insert;
    }
}

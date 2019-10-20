package com.example.c3p0.service;

import com.example.c3p0.domain.User;
import com.example.c3p0.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据业务层
 *
 * @author kevin
 * @date 2018-10-18 21:14
 **/
@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> getUsers(){
        return repo.getData();
    }

    public void insertUser(User user){
        repo.insertData(user);
    }
}

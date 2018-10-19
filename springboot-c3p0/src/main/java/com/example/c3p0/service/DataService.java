package com.example.c3p0.service;

import com.example.c3p0.domain.User;
import com.example.c3p0.repositories.DataRepositories;
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
public class DataService {

    @Autowired
    private DataRepositories repo;

    public List<User> getUsers(){
        return repo.getData();
    }

    public void insertUser(User user){
        repo.insertData(user);
    }
}

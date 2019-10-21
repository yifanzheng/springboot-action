package com.example.resource.datasource02.service;

import com.example.resource.datasource01.mapper.DataSource1Mapper;
import com.example.resource.datasource02.mapper.DataSource2Mapper;
import com.example.resource.domain.Book;
import com.example.resource.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kevin
 **/
@Service
public class DataSourceService2 {

    @Autowired
    private DataSource2Mapper mapper;

    public Book selectPlayer(String name) {
        Book book = mapper.selectByName(name);

        return book;
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager2") // 指定事务管理器
    public int insertPlayer(Book book) {

        if (book == null) {
            return 0;
        }

        int insert = mapper.insert(book.getId(), book.getName(), book.getPrice(), book.getPnum(), book.getCategory());
        return insert;
    }
}

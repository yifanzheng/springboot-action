package com.example.mybatis.service;

import com.example.mybatis.mapper.secondary.BookMapper;
import com.example.mybatis.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookService
 *
 * @author star
 **/
@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public Book selectPlayer(String name) {
        Book book = this.bookMapper.findByName(name);

        return book;
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager2") // 指定事务管理器
    public int insertPlayer(Book book) {
        if (book == null) {
            return 0;
        }

        int insert = this.bookMapper.insert(book.getId(), book.getName(), book.getNumber());
        return insert;
    }
}

package com.multi.jpa.service;

import com.multi.jpa.primarydatasource.domain.Book;
import com.multi.jpa.primarydatasource.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookService
 *
 * @author kevin
 **/
@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    public List<Book> getBookList() {
        return repo.findAll();
    }

    public int insertBook(Book book) {
        int i = repo.insert(book.getName(), book.getCategory());
        return i;
    }

    public int updateBook(Book book){
        int i = repo.updateById(book.getName(), book.getCategory(), book.getId());
        return i;
    }

    public void deleteBook(Integer id){
       repo.deleteById(id);
    }


}

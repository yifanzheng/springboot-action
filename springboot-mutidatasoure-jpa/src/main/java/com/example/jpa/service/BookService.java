package com.example.jpa.service;

import com.example.jpa.entity.primary.Book;
import com.example.jpa.repository.primary.BookRepository;
import com.example.jpa.service.dto.BookDTO;
import com.example.jpa.service.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookService
 *
 * @author star
 **/
@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> listBook(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        List<BookDTO> books = page.map(e -> bookMapper.convertForBook(e)).getContent();
        return books;
    }

    public BookDTO saveBook(BookDTO dto) {
        Book book = bookMapper.convertToBook(dto);
        bookRepository.save(book);

        return dto;
    }

    public BookDTO updateBook(BookDTO dto) {
        return saveBook(dto);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);

        logger.info("Delete book for id: {}", id);
    }
}

package com.example.jpa.service.mapper;

import com.example.jpa.entity.primary.Book;
import com.example.jpa.service.dto.BookDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * BookMapper
 *
 * @author star
 */
@Service
public class BookMapper {

    public Book convertToBook(BookDTO dto) {
        Book book = new Book();
        BeanUtils.copyProperties(dto, book);

        return book;
    }

    public BookDTO convertForBook(Book book) {
        BookDTO dto = new BookDTO();
        BeanUtils.copyProperties(book, dto);

        return dto;
    }
}

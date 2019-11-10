package com.example.jpa.repository.primary;

import com.example.jpa.entity.primary.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BookRepository
 *
 * @author star
 **/
public interface BookRepository extends JpaRepository<Book, Integer> {

}

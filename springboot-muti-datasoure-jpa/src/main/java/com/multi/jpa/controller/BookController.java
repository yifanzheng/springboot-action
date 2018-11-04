package com.multi.jpa.controller;

import com.multi.jpa.primarydatasource.domain.Book;
import com.multi.jpa.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController
 *
 * @author kevin
 **/
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBookList() {
        List<Book> bookList = service.getBookList();
        return ResponseEntity.ok(bookList);
    }

    @PostMapping("/book")
    public ResponseEntity<Object> insertBook(@RequestBody Book book) {
        int i = service.insertBook(book);
        return ResponseEntity.ok(String.format("Insert %s data success!"));
    }

    @PutMapping("/book")
    public ResponseEntity<Object> updateBook(@RequestBody Book book) {
        int i = service.updateBook(book);
        return ResponseEntity.ok(String.format("Update %s data success!"));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") Integer id){
        service.deleteBook(id);
        return ResponseEntity.ok("Delete data success!");
    }
}

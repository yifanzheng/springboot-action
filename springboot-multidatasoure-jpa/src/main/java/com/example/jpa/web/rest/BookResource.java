package com.example.jpa.web.rest;

import com.example.jpa.service.BookService;
import com.example.jpa.service.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookResource
 *
 * @author star
 **/
@RestController
@RequestMapping("/api")
public class BookResource {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBookList(Pageable pageable) {
        List<BookDTO> books = bookService.listBook(pageable);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO dto) {
        BookDTO bookDTO = bookService.saveBook(dto);
        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping("/books")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO dto) {
        BookDTO bookDTO = bookService.updateBook(dto);
        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") Integer id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}

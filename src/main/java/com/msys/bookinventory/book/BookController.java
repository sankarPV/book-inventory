package com.msys.bookinventory.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.msys.bookinventory.error.BookNotFound;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class BookController {

    @Autowired
    private BookRepository repository;

    // FindAll
    @GetMapping("/books")
    List<Book> findAll() {
        return repository.findAll();
    	
    }

    // Save
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@Valid @RequestBody Book newBook) {
        return repository.save(newBook);
    }

    // Find
    @GetMapping("/books/{id}")
    Book findOne(@PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFound(id));
    }

    // Save or update
    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }


    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }

}

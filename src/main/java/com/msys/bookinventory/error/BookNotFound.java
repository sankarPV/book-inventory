package com.msys.bookinventory.error;

public class BookNotFound extends RuntimeException {

    public BookNotFound(Long id) {
        super("Book id not found : " + id);
    }

}

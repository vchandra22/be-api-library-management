package com.enigma.library_management.service;


import com.enigma.library_management.dto.request.BookRequest;
import com.enigma.library_management.dto.request.SearchBookRequest;
import com.enigma.library_management.dto.response.BookResponse;
import com.enigma.library_management.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    BookResponse getBookById(String id);
    BookResponse createBook(BookRequest bookRequest);
    BookResponse updateBook(String id, BookRequest bookRequest);
    void deleteBook(String id);
    Page<BookResponse> getAllBook(SearchBookRequest searchBookRequest);
    Book getOne(String id);
}

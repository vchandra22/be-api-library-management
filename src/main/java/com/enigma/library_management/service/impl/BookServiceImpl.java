package com.enigma.library_management.service.impl;

import com.enigma.library_management.dto.request.BookRequest;
import com.enigma.library_management.dto.request.SearchBookRequest;
import com.enigma.library_management.dto.response.BookResponse;
import com.enigma.library_management.entity.Book;
import com.enigma.library_management.repository.BookRepository;
import com.enigma.library_management.service.BookService;
import com.enigma.library_management.specification.BookSpecification;
import com.enigma.library_management.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookResponse getBookById(String id) {
        Book book = getOne(id);

        return toBookResponse(book);
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .year(bookRequest.getYear())
                .build();

        bookRepository.saveAndFlush(book);

        return toBookResponse(book);
    }

    @Override
    public BookResponse updateBook(String id, BookRequest bookRequest) {
        Book currentBook = getOne(id);

        currentBook.setId(id);
        currentBook.setTitle(bookRequest.getTitle());
        currentBook.setAuthor(bookRequest.getAuthor());
        currentBook.setPublisher(bookRequest.getPublisher());
        currentBook.setYear(bookRequest.getYear());

        bookRepository.saveAndFlush(currentBook);

        return toBookResponse(currentBook);
    }

    @Override
    public void deleteBook(String id) {
        Book book = getOne(id);

        bookRepository.delete(book);
    }

    @Override
    public Page<BookResponse> getAllBook(SearchBookRequest searchBookRequest) {
        Pageable bookPageable = PageRequest.of(
                (searchBookRequest.getPage() - 1),
                searchBookRequest.getSize(),
                SortUtil.parseSortFromQueryParam(searchBookRequest.getSort())
        );

        Specification<Book> bookSpecification = BookSpecification.getSpecification(searchBookRequest);
        Page<Book> bookPage = bookRepository.findAll(bookSpecification, bookPageable);

        return bookPage.map(this::toBookResponse);
    }

    @Override
    public Book getOne(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Not Found"));
    }

    private BookResponse toBookResponse(Book book) {
        BookResponse bookResponse = new BookResponse();

        bookResponse.setId(String.valueOf(book.getId()));
        bookResponse.setTitle(book.getTitle());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setPublisher(book.getPublisher());
        bookResponse.setYear(book.getYear());

        return bookResponse;
    }
}

package com.enigma.library_management.service.impl;

import com.enigma.library_management.dto.request.BookRequest;
import com.enigma.library_management.dto.request.SearchBookRequest;
import com.enigma.library_management.dto.response.BookResponse;
import com.enigma.library_management.entity.Book;
import com.enigma.library_management.entity.Category;
import com.enigma.library_management.entity.Library;
import com.enigma.library_management.repository.BookRepository;
import com.enigma.library_management.repository.CategoryRepository;
import com.enigma.library_management.repository.LibraryRepository;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponse getBookById(String id) {
        Book book = getOne(id);

        return toBookResponse(book);
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        Library library = libraryRepository.findById(bookRequest.getLibrary())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Library Not Found"));

        List<Category> categories = categoryRepository.findAllById(bookRequest.getCategory());
        if (categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found");
        }

        Date yearDate = bookRequest.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearDate);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date formattedYear = calendar.getTime();

        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .year(formattedYear)
                .library(library)
                .categories(categories)
                .build();

        bookRepository.saveAndFlush(book);

        return toBookResponse(book);
    }

    @Override
    public BookResponse updateBook(String id, BookRequest bookRequest) {
        Book currentBook = getOne(id);

        Library library = libraryRepository.findById(bookRequest.getLibrary())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Library Not Found"));

        List<Category> categories = categoryRepository.findAllById(bookRequest.getCategory());
        if (categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found");
        }

        currentBook.setId(id);
        currentBook.setTitle(bookRequest.getTitle());
        currentBook.setAuthor(bookRequest.getAuthor());
        currentBook.setPublisher(bookRequest.getPublisher());
        currentBook.setYear(bookRequest.getYear());
        currentBook.setLibrary(library);
        currentBook.setCategories(categories);

        bookRepository.saveAndFlush(currentBook);

        return toBookResponse(currentBook);
    }

    @Override
    public void deleteBook(String id) {
        Book book = getOne(id);

        bookRepository.delete(book);
    }

    @Override
    public Page<BookResponse> getAll(SearchBookRequest searchBookRequest) {
        Pageable bookPageable = PageRequest.of(
                (searchBookRequest.getPage() - 1),
                searchBookRequest.getSize(),
                SortUtil.parseSortFromQueryParam(searchBookRequest.getSort())
        );
        Specification<Book> menuSpecification = BookSpecification.getSpecification(searchBookRequest);
        Page<Book> bookPage = bookRepository.findAll(menuSpecification, bookPageable);
        return bookPage.map(book -> toBookResponse(book));
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

        if (book.getLibrary() != null) {
            bookResponse.setLibrary(String.valueOf(book.getLibrary().getName()));
        }

        List<String> categories = book.getCategories().stream()
                .map(Category::getName)
                .toList();
        bookResponse.setCategory(categories);

        return bookResponse;
    }
}

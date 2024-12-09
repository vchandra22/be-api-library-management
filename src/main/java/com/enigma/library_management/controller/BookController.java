package com.enigma.library_management.controller;


import com.enigma.library_management.constant.Constant;
import com.enigma.library_management.dto.request.BookRequest;
import com.enigma.library_management.dto.request.SearchBookRequest;
import com.enigma.library_management.dto.response.BookResponse;
import com.enigma.library_management.service.BookService;
import com.enigma.library_management.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.BOOK_API)
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createNewBook(@RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.createBook(bookRequest);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_BOOK, bookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.updateBook(id, bookRequest);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_BOOK, bookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_DELETE_BOOK, null);
    }

    @GetMapping
    public ResponseEntity<?> getAllBook(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sort", defaultValue = "title") String sort
    ) {
        SearchBookRequest searchBookRequest = SearchBookRequest.builder()
                .title(title)
                .author(author)
                .page(page)
                .size(size)
                .sort(sort).build();

        Page<BookResponse> bookResponses = bookService.getAll(searchBookRequest);
        return ResponseUtil.buildPageResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_BOOK, bookResponses);
    }
}


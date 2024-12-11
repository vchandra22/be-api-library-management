package com.enigma.library_management.controller;

import com.enigma.library_management.constant.Constant;
import com.enigma.library_management.dto.request.LibraryRequest;
import com.enigma.library_management.dto.response.LibraryResponse;
import com.enigma.library_management.service.LibraryService;
import com.enigma.library_management.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.LIBRARY_API)
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<?> getAllLibrary() {
        List<LibraryResponse> libraryResponses = libraryService.getAll();

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_LIBRARY, libraryResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibrary(@PathVariable String id, @RequestBody LibraryRequest libraryRequest) {
        LibraryResponse libraryResponse = libraryService.updateLibrary(id, libraryRequest);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_LIBRARY, libraryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibrary(@PathVariable String id) {
        libraryService.deleteLibrary(id);

        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_DELETE_LIBRARY, null);
    }

    @PostMapping
    public ResponseEntity<?> createLibrary(@RequestBody LibraryRequest libraryRequest) {
        LibraryResponse libraryResponse = libraryService.createLibrary(libraryRequest);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_LIBRARY, libraryResponse);
    }
}

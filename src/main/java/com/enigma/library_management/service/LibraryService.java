package com.enigma.library_management.service;

import com.enigma.library_management.dto.request.LibraryRequest;
import com.enigma.library_management.dto.response.LibraryResponse;
import com.enigma.library_management.entity.Library;

import java.util.List;

public interface LibraryService {
    LibraryResponse getLibraryById(String id);
    LibraryResponse createLibrary(LibraryRequest libraryRequest);
    LibraryResponse updateLibrary(String id, LibraryRequest libraryRequest);
    void deleteLibrary(String id);
    Library getOne(String id);
    List<LibraryResponse> getAll();
}

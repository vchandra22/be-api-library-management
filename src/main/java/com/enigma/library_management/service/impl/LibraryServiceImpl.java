package com.enigma.library_management.service.impl;

import com.enigma.library_management.dto.request.LibraryRequest;
import com.enigma.library_management.dto.response.LibraryResponse;
import com.enigma.library_management.entity.Library;
import com.enigma.library_management.repository.LibraryRepository;
import com.enigma.library_management.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;

    @Override
    public LibraryResponse getLibraryById(String id) {
        Library library = getOne(id);

        return toLibraryResponse(library);
    }

    @Override
    public LibraryResponse createLibrary(LibraryRequest libraryRequest) {
        Library library = Library.builder()
                .name(libraryRequest.getName())
                .address(libraryRequest.getAddress())
                .build();

        libraryRepository.saveAndFlush(library);

        return toLibraryResponse(library);
    }

    @Override
    public LibraryResponse updateLibrary(String id, LibraryRequest libraryRequest) {
        Library library = getOne(id);

        library.setId(id);
        library.setName(libraryRequest.getName());
        library.setAddress(libraryRequest.getAddress());

        libraryRepository.saveAndFlush(library);

        return toLibraryResponse(library);
    }

    @Override
    public void deleteLibrary(String id) {
        Library library = getOne(id);

        libraryRepository.delete(library);
    }

    @Override
    public Library getOne(String id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Library Not Found"));
    }

    @Override
    public List<LibraryResponse> getAll() {
        return libraryRepository.findAll()
                .stream()
                .map(this::toLibraryResponse)
                .toList();
    }

    private LibraryResponse toLibraryResponse(Library library) {
        LibraryResponse libraryResponse = new LibraryResponse();

        libraryResponse.setId(library.getId());
        libraryResponse.setName(library.getName());
        libraryResponse.setAddress(library.getAddress());

        return libraryResponse;
    }
}

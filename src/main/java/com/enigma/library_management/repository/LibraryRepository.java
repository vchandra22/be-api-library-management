package com.enigma.library_management.repository;

import com.enigma.library_management.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String> {
    //
}

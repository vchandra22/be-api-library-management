package com.enigma.library_management.repository;

import com.enigma.library_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    //
}

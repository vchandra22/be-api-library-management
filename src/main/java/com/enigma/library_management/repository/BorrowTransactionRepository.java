package com.enigma.library_management.repository;

import com.enigma.library_management.entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, String> {
    //
}

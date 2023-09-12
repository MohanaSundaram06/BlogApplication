package com.mohan.BloggingSystem.Repository;

import com.mohan.BloggingSystem.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Optional<Author> findByEmail(String email);

    boolean existsByEmail(String email);
}

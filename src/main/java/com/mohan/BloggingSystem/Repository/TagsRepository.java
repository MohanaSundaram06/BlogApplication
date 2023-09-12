package com.mohan.BloggingSystem.Repository;

import com.mohan.BloggingSystem.Models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags,Integer> {
    boolean existsByName(String name);
}

package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.categoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface categoryRepository extends JpaRepository<categoryModel, Integer> {
    Optional<categoryModel> findByNameIgnoreCase(String name);
}

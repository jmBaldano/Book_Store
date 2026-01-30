package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.categoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepository extends JpaRepository<categoryModel, Integer> {
}

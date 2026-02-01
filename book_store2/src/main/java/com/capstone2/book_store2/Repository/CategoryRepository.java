package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {
}

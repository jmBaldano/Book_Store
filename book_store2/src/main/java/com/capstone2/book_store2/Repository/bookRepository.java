package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.bookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface bookRepository extends JpaRepository<bookModel, Integer> {
    List<bookModel> findByCategoryId(Integer categoryId);
    List<bookModel> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}

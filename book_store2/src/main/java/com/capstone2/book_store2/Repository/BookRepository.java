package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Integer> {
    List<BookModel> findByCategoryId(Integer categoryId);
    List<BookModel> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}

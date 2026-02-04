package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import  org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
    Page<BookModel> findByCategoryId(Integer categoryId, Pageable pageable);
    Page<BookModel> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);
    Page<BookModel> findByCategoryIdAndTitleContainingIgnoreCase(Integer categoryId, String title, Pageable pageable);
    Page<BookModel> findAll(Pageable page);

}

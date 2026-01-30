package com.capstone2.book_store2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.capstone2.book_store2.Model.bookModel;

import java.util.List;

@Repository
public interface bookRepository extends JpaRepository<bookModel, Integer>{

    //Find books by category id
    List<bookModel> findByCategory_Id(Integer categoryId);

    // Find books by category name
    List<bookModel> findByCategory_NameIgnoreCase(String name);

    // Find books where title contains term (optional helper)
    List<bookModel> findByTitleContainingIgnoreCase(String title);

}

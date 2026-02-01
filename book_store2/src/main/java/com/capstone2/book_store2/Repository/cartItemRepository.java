package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.cartItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface cartItemRepository extends JpaRepository<cartItemModel, Long> {
    List<cartItemModel> findByUsername(String username);
    void deleteByUsernameAndBookId(String username, Integer bookId);

}

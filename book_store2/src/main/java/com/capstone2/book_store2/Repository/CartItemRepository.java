package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.CartItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {
    List<CartItemModel> findByUsername(String username);
    void deleteByUsernameAndBookId(String username, Integer bookId);

}

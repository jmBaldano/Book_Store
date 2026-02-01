package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.CartItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {

    List<CartItemModel> findByUsername(String username);

    Optional<CartItemModel> findByUsernameAndBookId(String username, Long bookId);

    void deleteByUsername(String username);
}

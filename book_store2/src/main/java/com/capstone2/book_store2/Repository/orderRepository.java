package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.orderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderRepository extends JpaRepository<orderModel, Long> {
    List<orderModel> findByUsernameOrderByOrderDateDesc(String username);
}

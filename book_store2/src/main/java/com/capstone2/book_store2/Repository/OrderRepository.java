package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByUsernameOrderByOrderDateDesc(String username);
}

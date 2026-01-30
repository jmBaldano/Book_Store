package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<userModel, Integer> {
    Optional<userModel> findByUsername(String username);
}

package com.capstone2.book_store2.Repository;

import com.capstone2.book_store2.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    //checks if the username is unique or existing
    Optional<UserModel> findByUsername(String username);
}

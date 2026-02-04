package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.UserModel;
import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Register new user
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel user) {
        return adminService.registerUser(user);
    }

    // Add new book
    @PostMapping("/books")
    public String addBook(@RequestBody Map<String, Object> payload) {
        return adminService.addBook(payload);
    }

    // List all users
    @GetMapping("/users")
    public List<UserModel> getUsers() {
        return adminService.getAllUsers();
    }


    // List all books
    @GetMapping("/books")
    public List<BookModel> getBooks() {
        return adminService.getAllBooks();
    }
}

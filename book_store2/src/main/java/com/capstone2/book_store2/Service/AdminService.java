package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Model.CategoryModel;
import com.capstone2.book_store2.Model.UserModel;
import com.capstone2.book_store2.Repository.BookRepository;
import com.capstone2.book_store2.Repository.CategoryRepository;
import com.capstone2.book_store2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new user
    public String registerUser(UserModel user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepo.save(user);
        return "User registered successfully";
    }

    // Add a new book
    public String addBook(Map<String, Object> payload) {
        try {
            String title = (String) payload.get("title");
            String author = (String) payload.get("author");
            double price = Double.parseDouble(payload.get("price").toString());
            String description = (String) payload.get("description");
            String categoryName = (String) payload.get("category");

            // Find existing category or create a new one
            CategoryModel category = categoryRepo.findByName(categoryName)
                    .orElseGet(() -> categoryRepo.save(new CategoryModel(categoryName)));

            BookModel book = new BookModel();
            book.setTitle(title);
            book.setAuthor(author);
            book.setPrice(price);
            book.setDescription(description);
            book.setCategory(category);

            bookRepo.save(book);
            return "Book added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to add book: " + e.getMessage();
        }
    }

    // Get all users
    public List<UserModel> getAllUsers() {
        return userRepo.findAll();
    }

    // Get all books
    public List<BookModel> getAllBooks() {
        return bookRepo.findAll();
    }
}

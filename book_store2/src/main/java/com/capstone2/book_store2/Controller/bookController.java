package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.bookModel;
import com.capstone2.book_store2.Repository.categoryRepository;
import com.capstone2.book_store2.Service.bookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class bookController {

    private final bookService bookService;
    private final categoryRepository categoryRepo;

    // Inject Service + Repository
    public bookController(bookService bookService, categoryRepository categoryRepo) {
        this.bookService = bookService;
        this.categoryRepo = categoryRepo;
    }


    @GetMapping("/books")
    public String listBooks(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String q,
            Model model
    ) {

        // Ask the service for books (no logic here)
        List<bookModel> books = bookService.getBooks(categoryId, q);

        // Add attributes for Thymeleaf
        model.addAttribute("books", books);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("q", q);

        return "books"; // books.html
    }
}

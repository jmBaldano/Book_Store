package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.bookModel;
import com.capstone2.book_store2.Repository.bookRepository;
import com.capstone2.book_store2.Repository.categoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class bookController {

    private final bookRepository bookRepo;
    private final categoryRepository categoryRepo;

    public bookController(bookRepository bookRepo, categoryRepository categoryRepo) {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String q, Model model) {

        List<bookModel> books;

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasCategory = categoryId != null;

        if (hasCategory) {
            books = bookRepo.findByCategoryId(categoryId);
            if (hasQuery) {
                String lower = q == null ? "" : q.toLowerCase();
                books = books.stream()
                        .filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(lower))
                                || (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lower)))
                        .collect(Collectors.toList());
            }
        } else if (hasQuery) {
            books = bookRepo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q);
        } else {
            books = bookRepo.findAll();
        }

        model.addAttribute("books", books);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("q", q);

        return "books";
    }
}


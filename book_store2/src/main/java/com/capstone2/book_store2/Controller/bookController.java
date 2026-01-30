package com.capstone2.book_store2.Controller;


import com.capstone2.book_store2.Model.categoryModel;
import com.capstone2.book_store2.Model.bookModel;
import com.capstone2.book_store2.Service.bookService;
import com.capstone2.book_store2.Service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

    @Controller
    public class bookController {

        @Autowired
        private bookService bookService;

        @Autowired
        private categoryService categoryService;

        // GET /books?categoryId=2
        @GetMapping("/books")
        public String listBooks(@RequestParam(required = false) Integer categoryId, Model model) {
            List<bookModel> books = bookService.findByCategoryId(categoryId);
            List<categoryModel> categories = categoryService.findAll();

            model.addAttribute("books", books);
            model.addAttribute("categories", categories);
            model.addAttribute("selectedCategoryId", categoryId == null ? 0 : categoryId);

            return "books"; // maps to templates/books.html
        }
    }


package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Repository.CategoryRepository;
import com.capstone2.book_store2.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * The type Book controller.
 */
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepo;
    /**
     * List books string.
     *
     * @param categoryId the category id
     * @param q          the q
     * @param model      the model
     * @return the string
     */
    @GetMapping("/books")
    public String listBooks(@RequestParam(required = false) Integer categoryId,
                            @RequestParam(required = false) String q,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "8") int size,
                            Model model) {

        //ask service for books
        Page<BookModel> books = bookService.getBooks(categoryId, q, page, size);

        //attributes for Thymeleaf
        model.addAttribute("books", books);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("selectedCategory", categoryId);
        model.addAttribute("q", q);

        return "books";
    }

    /**
     * Book details string.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
    @GetMapping("/books/details")
    public String bookDetails(@RequestParam Long id, Model model) {
        BookModel book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book_details";
    }
}

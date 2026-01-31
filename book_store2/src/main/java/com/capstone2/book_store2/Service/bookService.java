package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.bookModel;
import com.capstone2.book_store2.Repository.bookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class bookService {

    private final bookRepository bookRepo;

    // Constructor injection (recommended)
    public bookService(bookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<bookModel> getBooks(Integer categoryId, String q) {

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasCategory = categoryId != null;

        List<bookModel> books;

        // Case 1: Filter by category
        if (hasCategory) {
            books = bookRepo.findByCategoryId(categoryId);

            // If search query exists, filter results
            if (hasQuery) {
                String lower = q.toLowerCase();
                books = books.stream().filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(lower))
                 || (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lower))).collect(Collectors.toList());
            }

            // Case 2: Search only
        } else if (hasQuery) {
            books = bookRepo
                    .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q);

            // Case 3: No filters → return all
        } else {
            books = bookRepo.findAll();
        }

        return books;
    }
}

package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepo;


    public List<BookModel> getBooks(Integer categoryId, String q) {

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasCategory = categoryId != null;

        List<BookModel> books;

        if (hasCategory) {
            books = bookRepo.findByCategoryId(categoryId);

            //
            if (hasQuery) {
                String lower = q.toLowerCase();
                books = books.stream().filter(b -> b.getTitle() != null && b.getTitle().toLowerCase().contains(lower)).collect(Collectors.toList());

            }

        } else if (hasQuery) {
            books = bookRepo
                    .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q); // a jpa repo method

        } else {
            books = bookRepo.findAll();
        }

        return books;
    }
    public BookModel getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}

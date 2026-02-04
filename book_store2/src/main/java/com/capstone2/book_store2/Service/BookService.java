package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The type Book service.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepo;


    /**
     * Gets books.
     *
     * @param categoryId the category id
     * @param q          the q
     * @return the books
     */
    public Page<BookModel> getBooks(Integer categoryId, String q, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasCategory = categoryId != null;
        if (hasCategory && hasQuery) {
            return bookRepo.findByCategoryIdAndTitleContainingIgnoreCase(categoryId, q, pageable);
        }
        else if (hasCategory) {
            return bookRepo.findByCategoryId(categoryId, pageable);
        }
        else if (hasQuery) {
            return bookRepo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q, pageable);
        }
        else {
            return bookRepo.findAll(pageable);
        }
    }



    /**
     * Gets book by id.
     *
     * @param id the id
     * @return the book by id
     */
    public BookModel getBookById(Long id) {
        return bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}

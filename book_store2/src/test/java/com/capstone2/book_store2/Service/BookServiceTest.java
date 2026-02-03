package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Book service test.
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    /**
     * The Book repo.
     */
    @Mock
    BookRepository bookRepo;

    /**
     * The Book service.
     */
    @InjectMocks
    BookService bookService;

    /**
     * Gets books no filters returns all.
     */
    @Test
    void getBooks_noFilters_returnsAll() {
        BookModel a = new BookModel(1L, "A", "Author A", 10.0, "desc", null);
        BookModel b = new BookModel(2L, "B", "Author B", 20.0, "desc", null);

        when(bookRepo.findAll()).thenReturn(List.of(a, b));

        List<BookModel> result = bookService.getBooks(null, null);

        assertEquals(2, result.size());
        verify(bookRepo).findAll();
    }

    /**
     * Gets book by id not found throws.
     */
    @Test
    void getBookById_notFound_throws() {
        when(bookRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookById(99L));

        verify(bookRepo).findById(99L);
    }
}
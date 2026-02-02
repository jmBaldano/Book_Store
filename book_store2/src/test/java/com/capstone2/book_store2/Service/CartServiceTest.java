package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Model.CartItemModel;
import com.capstone2.book_store2.Repository.BookRepository;
import com.capstone2.book_store2.Repository.CartItemRepository;
import com.capstone2.book_store2.Repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.print.Book;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class CartServiceTest {

    private CartService cartService;
    private CartItemRepository cartRepo;
    private BookRepository bookRepo;
    private OrderRepository orderRepo;



    @BeforeEach
    void setUp() {
        cartRepo = Mockito.mock(CartItemRepository.class);
        bookRepo = Mockito.mock(BookRepository.class);
        orderRepo = Mockito.mock(OrderRepository.class);
        cartService = new CartService(cartRepo, bookRepo, orderRepo);
    }

    @Test
    void getCart() {
        // mock a book
        BookModel book = new BookModel();
        book.setId(1L);

        // mock cartRepo behavior
        CartItemModel cartItem = new CartItemModel();
        cartItem.setUsername("john");
        cartItem.setBook(book);
        cartItem.setQuantity(1);

        when(cartRepo.findByUsername("john")).thenReturn(List.of(cartItem));

        // fetch the cart
        List<CartItemModel> cartItems = cartService.getCart("john");

        // assertions
        assertEquals(1, cartItems.size());
        assertEquals("john", cartItems.get(0).getUsername());
        assertEquals(1L, cartItems.get(0).getBook().getId());
        assertEquals(1, cartItems.get(0).getQuantity());
    }
    @Test
    void addToCart() {
    }

    @Test
    void removeFromCart() {
    }

    @Test
    void checkout() {
    }

    @Test
    void getOrderHistory() {
    }
}
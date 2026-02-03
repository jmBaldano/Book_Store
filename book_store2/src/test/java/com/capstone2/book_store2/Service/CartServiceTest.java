package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Model.CartItemModel;
import com.capstone2.book_store2.Model.OrderModel;
import com.capstone2.book_store2.Repository.BookRepository;
import com.capstone2.book_store2.Repository.CartItemRepository;
import com.capstone2.book_store2.Repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Cart service test.
 */
@RequiredArgsConstructor
class CartServiceTest {

    private CartService cartService;
    private CartItemRepository cartRepo;
    private BookRepository bookRepo;
    private OrderRepository orderRepo;


    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        cartRepo = Mockito.mock(CartItemRepository.class);
        bookRepo = Mockito.mock(BookRepository.class);
        orderRepo = Mockito.mock(OrderRepository.class);
        cartService = new CartService(cartRepo, bookRepo, orderRepo);
    }

    /**
     * Gets cart.
     */
    @Test
    void getCart() {
        // mock cartRepo behavior
        BookModel book = new BookModel();
        book.setId(1L);

        when(cartRepo.findByUsername("john")).thenReturn(List.of(
                new CartItemModel() {{
                    setUsername("john");
                    setBook(book);
                    setQuantity(1);
                }}
        ));

        List<CartItemModel> cartItems = cartService.getCart("john");

        assertEquals(1, cartItems.size());
        assertEquals("john", cartItems.get(0).getUsername());
        assertEquals(1L, cartItems.get(0).getBook().getId());
        assertEquals(1, cartItems.get(0).getQuantity());
    }

    /**
     * Add to cart creates new item when none exists.
     */
    @Test
    void addToCart_createsNewItem_whenNoneExists() {
        BookModel book = new BookModel();
        book.setId(1L);

        when(cartRepo.findByUsernameAndBookId("john", 1L)).thenReturn(Optional.empty());
        when(bookRepo.findById(1L)).thenReturn(Optional.of(book));
        when(cartRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        cartService.addToCart("john", 1L);

        ArgumentCaptor<CartItemModel> captor = ArgumentCaptor.forClass(CartItemModel.class);
        verify(cartRepo).save(captor.capture());
        CartItemModel saved = captor.getValue();

        assertEquals("john", saved.getUsername());
        assertEquals(1L, saved.getBook().getId());
        assertEquals(1, saved.getQuantity());
    }

    /**
     * Checkout saves order and clears cart.
     */
    @Test
    void checkout_savesOrderAndClearsCart() {
        BookModel book = new BookModel(1L, "Title", "Author", 5.0, "desc", null);
        CartItemModel ci = new CartItemModel();
        ci.setUsername("john");
        ci.setBook(book);
        ci.setQuantity(2);

        when(cartRepo.findByUsername("john")).thenReturn(List.of(ci));
        when(orderRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        OrderModel order = cartService.checkout("john");

        assertNotNull(order);
        assertEquals("john", order.getUsername());
        assertEquals(10.0, order.getTotalAmount(), 0.001);
        verify(orderRepo).save(any());
        verify(cartRepo).deleteByUsername("john");
    }

    /**
     * Remove from cart.
     */
    @Test
    void removeFromCart() {
    }

    /**
     * Gets order history.
     */
    @Test
    void getOrderHistory() {
    }
}
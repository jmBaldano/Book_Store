package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.*;
import com.capstone2.book_store2.Repository.BookRepository;
import com.capstone2.book_store2.Repository.CartItemRepository;
import com.capstone2.book_store2.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Cart service.
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartRepo;
    private final BookRepository bookRepo;
    private final OrderRepository orderRepo;

    /**
     * Gets cart.
     *
     * @param username the username
     * @return the cart
     */
    public List<CartItemModel> getCart(String username) {
        return cartRepo.findByUsername(username);
    }

    /**
     * Add to cart.
     *
     * @param username the username
     * @param bookId   the book id
     */
    @Transactional
    public void addToCart(String username, Long bookId) {
        CartItemModel item = cartRepo
                .findByUsernameAndBookId(username, bookId)
                .orElseGet(() -> {
                    CartItemModel newItem = new CartItemModel();
                    newItem.setUsername(username);
                    newItem.setBook(bookRepo.findById(bookId)
                            .orElseThrow(() -> new RuntimeException("Book not found")));
                    newItem.setQuantity(0);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + 1);
        cartRepo.save(item);
    }

    /**
     * Remove from cart.
     *
     * @param username   the username
     * @param cartItemId the cart item id
     */
    @Transactional
    public void removeFromCart(String username, Long cartItemId) {
        CartItemModel item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUsername().equals(username)) {
            throw new RuntimeException("Not authorized");
        }

        cartRepo.delete(item);
    }

    /**
     * Checkout order model.
     *
     * @param username the username
     * @return the order model
     */
    @Transactional
    public OrderModel checkout(String username) {

        List<CartItemModel> cartItems = cartRepo.findByUsername(username);
        if (cartItems.isEmpty()) return null;

        OrderModel order = new OrderModel();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());

        // Create order items
        List<OrderItemModel> orderItems = cartItems.stream().map(ci -> {
            OrderItemModel oi = new OrderItemModel();
            oi.setBookId(ci.getBook().getId());   //ensure bookId is set
            oi.setBook(ci.getBook());             // keep book entity for JSON
            oi.setTitle(ci.getBook().getTitle());
            oi.setPrice(ci.getBook().getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setOrder(order);
            return oi;
        }).toList();

        order.setOrderItems(orderItems);

        // calculate total
        double total = 0;
        for (OrderItemModel item : orderItems) total += item.getPrice() * item.getQuantity();
        order.setTotalAmount(total);


        // save and delete cart
        orderRepo.save(order);
        cartRepo.deleteByUsername(username);

        return order;
    }

    /**
     * Gets order history.
     *
     * @param username the username
     * @return the order history
     */
    public List<OrderModel> getOrderHistory(String username) {
        return orderRepo.findByUsernameOrderByOrderDateDesc(username);
    }
}

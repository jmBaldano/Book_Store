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

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartRepo;
    private final BookRepository bookRepo;
    private final OrderRepository orderRepo;

    // =================== GET CART ===================
    public List<CartItemModel> getCart(String username) {
        return cartRepo.findByUsername(username);
    }

    // =================== ADD TO CART ===================
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

    // =================== REMOVE FROM CART ===================
    @Transactional
    public void removeFromCart(String username, Long cartItemId) {
        CartItemModel item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUsername().equals(username)) {
            throw new RuntimeException("Not authorized");
        }

        cartRepo.delete(item);
    }

    // =================== CHECKOUT ===================
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
            oi.setBookId(ci.getBook().getId());   // FIX: ensure bookId is set
            oi.setBook(ci.getBook());             // keep book entity for JSON
            oi.setTitle(ci.getBook().getTitle());
            oi.setPrice(ci.getBook().getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setOrder(order);
            return oi;
        }).toList();

        order.setOrderItems(orderItems);

        // Calculate total
        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);

        // Save order and clear cart
        orderRepo.save(order);
        cartRepo.deleteByUsername(username);

        return order;
    }

    // =================== GET ORDER HISTORY ===================
    public List<OrderModel> getOrderHistory(String username) {
        return orderRepo.findByUsernameOrderByOrderDateDesc(username);
    }
}

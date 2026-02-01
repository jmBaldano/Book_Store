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

    // Get cart
    public List<CartItemModel> getCart(String username) {
        return cartRepo.findByUsername(username);
    }

    // Add or increment item
    @Transactional
    public void addToCart(String username, Long bookId) {

        CartItemModel item = cartRepo
                .findByUsernameAndBookId(username, bookId)
                .orElseGet(() -> {
                    CartItemModel newItem = new CartItemModel();
                    newItem.setUsername(username);
                    newItem.setBook(bookRepo.findById(bookId).orElseThrow());
                    newItem.setQuantity(0);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + 1);
        cartRepo.save(item);
    }

    // Remove item
    @Transactional
    public void removeFromCart(String username, Long cartItemId) {
        cartRepo.deleteById(cartItemId);
    }

    // Checkout
    @Transactional
    public OrderModel checkout(String username) {

        List<CartItemModel> cartItems = cartRepo.findByUsername(username);
        if (cartItems.isEmpty()) return null;

        OrderModel order = new OrderModel();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItemModel> orderItems = cartItems.stream().map(ci -> {
            OrderItemModel item = new OrderItemModel();
            item.setBook(ci.getBook());
            item.setTitle(ci.getBook().getTitle());
            item.setPrice(ci.getBook().getPrice());
            item.setQuantity(ci.getQuantity());
            item.setOrder(order);
            return item;
        }).toList();

        order.setOrderItems(orderItems);

        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        order.setTotalAmount(total);

        orderRepo.save(order);
        cartRepo.deleteByUsername(username);

        return order;
    }

    public List<OrderModel> getOrderHistory(String username) {
        return orderRepo.findByUsernameOrderByOrderDateDesc(username);
    }

}

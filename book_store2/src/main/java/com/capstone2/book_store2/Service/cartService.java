// CartService.java
package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.bookModel;
import com.capstone2.book_store2.Model.cartItemModel;
import com.capstone2.book_store2.Model.orderModel;
import com.capstone2.book_store2.Model.orderItemModel;
import com.capstone2.book_store2.Repository.bookRepository;
import com.capstone2.book_store2.Repository.cartItemRepository;
import com.capstone2.book_store2.Repository.orderRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
public class cartService {

    private final cartItemRepository cartItemRepository;
    private final orderRepository orderRepository;
    private final bookRepository bookRepository;

    public cartService(cartItemRepository cartItemRepository, orderRepository orderRepository, bookRepository bookRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    // Get cart items for a user
    public List<cartItemModel> getCart(String username) {
        return cartItemRepository.findByUsername(username);
    }

    // Add item to cart
    public cartItemModel addToCart(cartItemModel item) {
        return cartItemRepository.save(item);
    }

    // Remove item from cart
    @Transactional
    public void removeFromCart(String username, Integer bookId) {
        cartItemRepository.deleteByUsernameAndBookId(username, bookId);
    }

    // Checkout and create order
    @Transactional
    public orderModel checkout(String username) {
        List<cartItemModel> cartItems = cartItemRepository.findByUsername(username);
        if (cartItems.isEmpty()) return null;

        orderModel order = new orderModel();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());

        List<orderItemModel> orderItems = cartItems.stream().map(cartItem -> {
            orderItemModel orderItem = new orderItemModel();
            orderItem.setBookId(cartItem.getBookId());
            orderItem.setTitle(cartItem.getTitle());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            bookModel book = bookRepository.findById(cartItem.getBookId()).orElse(null);
            orderItem.setBook(book);
            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);
        double total = orderItems.stream().mapToDouble(oi -> oi.getPrice() * oi.getQuantity()).sum();
        order.setTotalAmount(total);

        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems); // clear cart after checkout


        return order;
    }

    // Get order history
    public List<orderModel> getOrderHistory(String username) {
        List<orderModel> orders = orderRepository.findByUsernameOrderByOrderDateDesc(username);

        // Set book for each order item
        for (orderModel order : orders) {
            for (orderItemModel item : order.getOrderItems()) {
                if (item.getBook() == null) {
                    item.setBook(bookRepository.findById(item.getBookId()).orElse(null));
                }
            }
        }
        return orders;
    }

}

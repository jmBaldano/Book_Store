// CartService.java
package com.capstone2.book_store2.Service;

import com.capstone2.book_store2.Model.BookModel;
import com.capstone2.book_store2.Model.CartItemModel;
import com.capstone2.book_store2.Model.OrderModel;
import com.capstone2.book_store2.Model.OrderItemModel;
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

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    // Get cart items for a user
    public List<CartItemModel> getCart(String username) {
        return cartItemRepository.findByUsername(username);
    }

    // Add item to cart
    public CartItemModel addToCart(CartItemModel item) {
        return cartItemRepository.save(item);
    }

    // Remove item from cart
    @Transactional
    public void removeFromCart(String username, Integer bookId) {
        cartItemRepository.deleteByUsernameAndBookId(username, bookId);
    }

    // Checkout and create order
    @Transactional
    public OrderModel checkout(String username) {
        List<CartItemModel> cartItems = cartItemRepository.findByUsername(username);
        if (cartItems.isEmpty()) return null;

        OrderModel order = new OrderModel();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItemModel> orderItems = cartItems.stream().map(cartItem -> {
            OrderItemModel orderItem = new OrderItemModel();
            orderItem.setBookId(cartItem.getBookId());
            orderItem.setTitle(cartItem.getTitle());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            BookModel book = bookRepository.findById(cartItem.getBookId()).orElse(null);
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
    public List<OrderModel> getOrderHistory(String username) {
        List<OrderModel> orders = orderRepository.findByUsernameOrderByOrderDateDesc(username);

        // Set book for each order item
        for (OrderModel order : orders) {
            for (OrderItemModel item : order.getOrderItems()) {
                if (item.getBook() == null) {
                    item.setBook(bookRepository.findById(item.getBookId()).orElse(null));
                }
            }
        }
        return orders;
    }

}

package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.CartItemModel;
import com.capstone2.book_store2.Model.OrderModel;
import com.capstone2.book_store2.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    // Get cart items
    @GetMapping
    public List<CartItemModel> getCart(Authentication auth) {
        return cartService.getCart(auth.getName());
    }

    // Add book to cart
    @PostMapping("/add/{bookId}")
    public void addToCart(@PathVariable Long bookId, Authentication auth) {
        cartService.addToCart(auth.getName(), bookId);
    }

    // Remove cart item
    @DeleteMapping("/remove/{cartItemId}")
    public void removeFromCart(@PathVariable Long cartItemId, Authentication auth) {
        cartService.removeFromCart(auth.getName(), cartItemId);
    }

    @PostMapping("/checkout")
    public OrderModel checkout(Authentication auth) {
        return cartService.checkout(auth.getName());
    }

    @GetMapping("/history")
    public List<OrderModel> orderHistory(Authentication auth) {
        return cartService.getOrderHistory(auth.getName());
    }
}

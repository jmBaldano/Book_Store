// CartController.java
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

    // Get current user's cart
    @GetMapping
    public List<CartItemModel> getCart(Authentication authentication) {
        String username = authentication.getName();
        return cartService.getCart(username);
    }

    // Add to cart
    @PostMapping("/add")
    public CartItemModel addToCart(@RequestBody CartItemModel item, Authentication authentication) {
        String username = authentication.getName();
        item.setUsername(username); // set username in item
        return cartService.addToCart(item);
    }

    // Remove from cart
    @DeleteMapping("/remove/{bookId}")
    public void removeFromCart(@PathVariable Integer bookId, Authentication authentication) {
        String username = authentication.getName();
        cartService.removeFromCart(username, bookId);
    }

    // Checkout
    @PostMapping("/checkout")
    public OrderModel checkout(Authentication authentication) {
        String username = authentication.getName();
        return cartService.checkout(username);
    }

    // Order history
    @GetMapping("/history")
    public List<OrderModel> getOrderHistory(Authentication authentication) {
        String username = authentication.getName();
        return cartService.getOrderHistory(username);
    }
}


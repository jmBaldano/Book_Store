// CartController.java
package com.capstone2.book_store2.Controller;

import com.capstone2.book_store2.Model.cartItemModel;
import com.capstone2.book_store2.Model.orderModel;
import com.capstone2.book_store2.Service.cartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class cartController {

    private final cartService cartService;

    public cartController(cartService cartService) {
        this.cartService = cartService;
    }

    // Get current user's cart
    @GetMapping
    public List<cartItemModel> getCart(Authentication authentication) {
        String username = authentication.getName();
        return cartService.getCart(username);
    }

    // Add to cart
    @PostMapping("/add")
    public cartItemModel addToCart(@RequestBody cartItemModel item, Authentication authentication) {
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
    public orderModel checkout(Authentication authentication) {
        String username = authentication.getName();
        return cartService.checkout(username);
    }

    // Order history
    @GetMapping("/history")
    public List<orderModel> getOrderHistory(Authentication authentication) {
        String username = authentication.getName();
        return cartService.getOrderHistory(username);
    }
}


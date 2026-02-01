package com.capstone2.book_store2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/cart")
    public String cartPage() {
        return "cart"; // Spring resolves this to cart.html in templates
    }

    @GetMapping("/orders")
    public String ordersPage() {
        return "orders"; // if you want /orders page
    }



    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }
}


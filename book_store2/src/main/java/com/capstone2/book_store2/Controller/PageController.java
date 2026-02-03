package com.capstone2.book_store2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    //these handles the http request for the cart and order history feature

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/orders")
    public String ordersPage() {
        return "orders";
    }


    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }
}

